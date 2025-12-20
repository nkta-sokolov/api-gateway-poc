package com.sokolov.hashunlocker.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sokolov.hashunlocker.persistence.model.Hash;

public interface HashRepository extends JpaRepository<Hash, Long> {

    Optional<Hash> findByDigest(String digest);

}