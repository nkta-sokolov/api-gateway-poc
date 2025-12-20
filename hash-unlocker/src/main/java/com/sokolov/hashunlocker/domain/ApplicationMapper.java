package com.sokolov.hashunlocker.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.sokolov.hashunlocker.persistence.model.Application;
import com.sokolov.hashunlocker.persistence.model.ApplicationStatus;

@Component
public class ApplicationMapper {

    public Application toEntity() {
        return new Application()
                .setStatus(ApplicationStatus.NEW)
                .setCreatedAt(OffsetDateTime.now())
                .setExternalId(UUID.randomUUID());
    }

}