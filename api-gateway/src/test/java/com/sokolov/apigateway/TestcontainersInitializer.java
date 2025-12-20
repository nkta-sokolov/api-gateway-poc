package com.sokolov.apigateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final DockerImageName REDIS_IMAGE =
            DockerImageName.parse("redis:7.2-alpine");

    private static final GenericContainer<?> REDIS = new GenericContainer<>(REDIS_IMAGE)
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());

    static {
        Startables.deepStart(REDIS).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Map<String, String> properties = createProperties();

        TestPropertyValues.of(properties)
                .applyTo(applicationContext.getEnvironment());
    }

    private Map<String, String> createProperties() {
        Map<String, String> properties = new HashMap<>();

        properties.put("tc.redis.host", REDIS.getHost());
        properties.put("tc.redis.port", String.valueOf(REDIS.getFirstMappedPort()));

        return properties;
    }

}