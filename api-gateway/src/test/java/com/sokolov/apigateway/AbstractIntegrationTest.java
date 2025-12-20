package com.sokolov.apigateway;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import reactor.core.publisher.Flux;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @RegisterExtension
    protected static final WireMockExtension WIRE_MOCK_EXTENSION = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void dynamicProps(DynamicPropertyRegistry registry) {
        registry.add("hash-unlocker.base-uri", () -> "http://localhost:" + WIRE_MOCK_EXTENSION.getPort());
    }

    protected WebTestClient.RequestHeadersSpec<?> postApplications(String apiKey, String bodyJson) {
        WebTestClient.RequestBodySpec spec = webTestClient.post()
                .uri("/api/public/applications")
                .header("Content-Type", "application/json");

        if (apiKey != null) {
            spec.header("X-API-KEY", apiKey);
        }

        return spec.bodyValue(bodyJson);
    }

    @BeforeEach
    void purgeCache() {
        Flux<String> keys = reactiveStringRedisTemplate.keys("*");
        reactiveStringRedisTemplate.delete(keys).block();
    }

}