package com.nps.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BusinessException extends RuntimeException {
    private final ErrorType type;
    private final ZonedDateTime failureTime;
    private final BusinessError details;

    public BusinessException(BusinessError details) {
        this.type = ErrorType.business;
        this.failureTime = ZonedDateTime.now();
        this.details = details;
    }

    public BusinessException(BusinessErrorCode errorCode) {
        this.type = ErrorType.business;
        this.failureTime = ZonedDateTime.now();
        this.details = new BusinessError(errorCode);
    }
}
