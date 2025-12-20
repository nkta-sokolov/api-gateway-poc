package com.sokolov.hashunlocker.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sokolov.hashunlocker.persistence.HashRepository;
import com.sokolov.hashunlocker.persistence.model.Application;
import com.sokolov.hashunlocker.persistence.model.Hash;
import com.sokolov.hashunlocker.rest.dto.ApplicationRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class HashService {

    private final HashRepository hashRepository;

    @Transactional(readOnly = true)
    public Optional<Hash> findByDigest(String digest) {
        return hashRepository.findByDigest(digest);
    }

    public void save(Application application, ApplicationRequestDto applicationRequestDto) {
        Hash hash = createHash(application, applicationRequestDto);

        hashRepository.save(hash);
    }

    private Hash createHash(Application application, ApplicationRequestDto applicationRequestDto) {
        return new Hash()
                .setApplication(application)
                .setDigest(applicationRequestDto.getDigest())
                .setAlgorithm(applicationRequestDto.getAlgorithm().name());
    }

}