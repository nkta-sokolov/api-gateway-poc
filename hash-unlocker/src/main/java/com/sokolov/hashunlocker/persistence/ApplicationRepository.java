package com.sokolov.hashunlocker.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sokolov.hashunlocker.persistence.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}