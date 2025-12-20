package com.sokolov.hashunlocker.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequestDto {

    @NotBlank
    private String digest;

    @NotNull
    private Algorithm algorithm;

}