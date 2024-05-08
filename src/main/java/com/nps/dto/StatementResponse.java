package com.nps.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class StatementResponse {
    private UUID id;
    private String paymentDueDate;
}
