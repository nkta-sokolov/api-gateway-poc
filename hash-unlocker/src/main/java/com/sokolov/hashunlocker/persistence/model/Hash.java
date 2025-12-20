package com.sokolov.hashunlocker.persistence.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Hash {

    @Id
    private Long applicationId;

    @Column(nullable = false)
    private String digest;

    @Column(nullable = false)
    private String algorithm;

    private String plaintext;

    @MapsId
    @Fetch(FetchMode.JOIN)
    @OneToOne(optional = false)
    private Application application;

}