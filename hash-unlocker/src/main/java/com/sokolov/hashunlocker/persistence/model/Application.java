package com.sokolov.hashunlocker.persistence.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@SequenceGenerator(name = "applicationSeqGenerator", sequenceName = "application_id_seq", allocationSize = 1)
public class Application {

    @Id
    @GeneratedValue(generator = "applicationSeqGenerator")
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

}