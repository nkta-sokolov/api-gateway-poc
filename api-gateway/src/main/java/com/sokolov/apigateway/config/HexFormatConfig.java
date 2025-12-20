package com.sokolov.apigateway.config;

import java.util.HexFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexFormatConfig {

    @Bean
    public HexFormat hexFormat() {
        return HexFormat.of();
    }

}