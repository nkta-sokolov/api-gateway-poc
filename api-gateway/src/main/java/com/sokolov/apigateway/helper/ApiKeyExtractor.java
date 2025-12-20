package com.sokolov.apigateway.helper;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sokolov.apigateway.config.properties.SecurityProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiKeyExtractor {

    private static final String X_API_KEY = "X-Api-Key";

    private final SecurityProperties securityProperties;

    public String extract(ServerHttpRequest request) {
        String apiKey = request.getHeaders().getFirst(X_API_KEY);

        if (!StringUtils.hasText(apiKey)) {
            log.error("Api-Key is absent");
            return null;
        }

        if (!securityProperties.getApiKey().allowedKeys().contains(apiKey)) {
            log.error("Api-Key {} is not allowed", apiKey);
            return null;
        }

        return apiKey;
    }

}