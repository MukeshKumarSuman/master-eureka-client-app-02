package com.nps.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class App03Exception extends RuntimeException {
    private final ErrorType type;
    private final ZonedDateTime failureTime;

    public App03Exception() {
        this.type = ErrorType.system;
        this.failureTime = ZonedDateTime.now();
    }

    public App03Exception(ErrorType type) {
        this.type = type;
        this.failureTime = ZonedDateTime.now();;
    }
}
