package com.nps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest {
    private String txId;
    private float amount;
    private Integer exponent;
}
