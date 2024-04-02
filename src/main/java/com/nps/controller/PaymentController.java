package com.nps.controller;

import com.nps.dto.PaymentRequest;
import com.nps.dto.PaymentResponse;
import com.nps.feignclient.PaymentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private static  final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentClient restClient;

    @PostMapping
    PaymentResponse initializePayment(@RequestBody PaymentRequest request) {
        logger.info("Request Received: {}", request);
        restClient.initializePayment(request);
        return new PaymentResponse(request.getTxId(), request.getAmount());
    }
}
