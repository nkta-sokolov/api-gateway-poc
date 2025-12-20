package com.sokolov.apigateway.helper;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApiKeyResolver implements KeyResolver {

    private final ApiKeyExtractor apiKeyExtractor;

    private final HashCalculator hashCalculator;

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.justOrEmpty(apiKeyExtractor.extract(exchange.getRequest()))
                .map(hashCalculator::sha256Hex);
    }

}