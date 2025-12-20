package com.sokolov.hashunlocker.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sokolov.hashunlocker.domain.ApplicationService;
import com.sokolov.hashunlocker.rest.dto.ApplicationDto;
import com.sokolov.hashunlocker.rest.dto.ApplicationRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/local/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ApplicationDto createApplication(@RequestBody ApplicationRequestDto applicationRequestDto) {
        return applicationService.save(applicationRequestDto);
    }

}