package com.sokolov.hashunlocker.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sokolov.hashunlocker.persistence.ApplicationRepository;
import com.sokolov.hashunlocker.persistence.model.Application;
import com.sokolov.hashunlocker.persistence.model.Hash;
import com.sokolov.hashunlocker.rest.dto.ApplicationDto;
import com.sokolov.hashunlocker.rest.dto.ApplicationRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    private final HashService hashService;

    @Value("${fail-on-save}")
    private final boolean failOnSave;

    public ApplicationDto save(ApplicationRequestDto applicationRequestDto) {
        if (failOnSave) {
            throw new RuntimeException("Failed to save application %s".formatted(applicationRequestDto));
        }

        return hashService.findByDigest(applicationRequestDto.getDigest())
                .map(this::fromExistingHash)
                .orElseGet(() -> saveNewApplication(applicationRequestDto));
    }

    private ApplicationDto fromExistingHash(Hash hash) {
        Application application = hash.getApplication();
        log.warn("Application {} already exists", application.getId());
        return new ApplicationDto()
                .setExternalId(application.getExternalId().toString());
    }

    private ApplicationDto saveNewApplication(ApplicationRequestDto applicationRequestDto) {
        Application application = applicationMapper.toEntity();

        Application savedApplication = applicationRepository.save(application);

        hashService.save(savedApplication, applicationRequestDto);

        return new ApplicationDto()
                .setExternalId(savedApplication.getExternalId().toString());
    }

}