package com.sokolov.apigateway.config.properties;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("security")
public class SecurityProperties {

    private ApiKey apiKey;

    public record ApiKey(Set<String> allowedKeys) {}

}