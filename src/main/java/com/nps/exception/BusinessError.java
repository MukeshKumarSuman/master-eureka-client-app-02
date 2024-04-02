package com.nps.exception;

import lombok.Data;

@Data
public class BusinessError {
    private final BusinessErrorCode code;
    private String additionalInformation;

    public BusinessError(BusinessErrorCode code) {
        this.code = code;
        this.additionalInformation = null;
    }

    public BusinessError(BusinessErrorCode code, String additionalInformation) {
        this.code = code;
        this.additionalInformation = additionalInformation;
    }
}
