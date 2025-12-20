package com.sokolov.apigateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Tag("integration")
public class GatewayIT extends AbstractIntegrationTest {

    private static final String API_KEY = "test";

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    void reset() {
        WIRE_MOCK_EXTENSION.resetAll();
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("applicationsCB");
        cb.reset();
    }

    @Test
    void shouldReturn403IfNoApiKey() {
        postApplications(null, "{}")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldReturn503AsFallbackAfterRetriesFor501FromBackend() {
        WIRE_MOCK_EXTENSION.stubFor(post(urlPathEqualTo("/api/local/applications"))
                .willReturn(aResponse().withStatus(500)));

        postApplications(API_KEY, "{}")
                .exchange()
                .expectStatus().isEqualTo(503)
                .expectHeader().contentTypeCompatibleWith("application/json")
                .expectBody()
                .jsonPath("$.error").isEqualTo("UPSTREAM_UNAVAILABLE");

        WIRE_MOCK_EXTENSION.verify(3, postRequestedFor(urlPathEqualTo("/api/local/applications")));
    }

    @Test
    void shouldOpenCircuitBreakerAfter5Failures() {
        WIRE_MOCK_EXTENSION.stubFor(post(urlPathMatching("/api/local/applications.*"))
                .willReturn(aResponse().withStatus(500)));

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("applicationsCB");

        for (int i = 0; i < 5; i++) {
            postApplications(API_KEY, "{}")
                    .exchange()
                    .expectStatus().isEqualTo(503);
        }

        Awaitility.await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN));
    }

    @Test
    void shouldReturn200FromBackend() {
        String body = """
                {
                  "result": "ok"
                }
                """;

        WIRE_MOCK_EXTENSION.stubFor(post(urlEqualTo("/api/local/applications"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)));

        postApplications(API_KEY, "{}")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.result").isEqualTo("ok");

        WIRE_MOCK_EXTENSION.verify(postRequestedFor(urlEqualTo("/api/local/applications")));
    }

}