package com.sokolov.hashunlocker.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sokolov.hashunlocker.rest.dto.ErrorDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails handleInternalError(Throwable throwable) {
        log.error("Unexpected error", throwable);
        return new ErrorDetails(throwable.getMessage());
    }

}