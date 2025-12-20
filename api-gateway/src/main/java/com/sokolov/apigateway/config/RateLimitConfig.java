package com.sokolov.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sokolov.apigateway.helper.ApiKeyExtractor;
import com.sokolov.apigateway.helper.ApiKeyResolver;
import com.sokolov.apigateway.helper.HashCalculator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RateLimitConfig {

    private final HashCalculator hashCalculator;

    private final ApiKeyExtractor apiKeyExtractor;

    @Bean
    public KeyResolver apiKeyResolver() {
        return new ApiKeyResolver(apiKeyExtractor, hashCalculator);
    }

}