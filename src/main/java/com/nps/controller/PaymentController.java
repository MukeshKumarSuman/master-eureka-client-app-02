package com.nps.controller;

import com.nps.dto.PaymentRequest;
import com.nps.dto.PaymentResponse;
import com.nps.dto.StatementResponse;
import com.nps.feignclient.PaymentClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@RestController
@RequestMapping("/v1")
public class PaymentController {
    private static  final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentClient restClient;

    @PostMapping("/payment")
    PaymentResponse initializePayment(@RequestBody PaymentRequest request) {
        logger.info("Request Received: {}", request);
        restClient.initializePayment(request);
        return new PaymentResponse(request.getTxId(), request.getAmount());
    }

    @GetMapping("/statement")
    public StatementResponse getStatement() {
        logger.info("Fetching Statement....");
        StatementResponse statement= restClient.getStatement();
        logger.info("Statement Received: {}", statement);
        TemporalAccessor parse = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(statement.getPaymentDueDate());
        Instant from = Instant.from(parse);
        Date date = Date.from(from);
        DateTime dateTime = new DateTime(date);
        logger.info("Statement parse date time: {}", dateTime);
        System.out.println(dateTime);
        return statement;
    }

    @GetMapping("/demo")
    public String demo() {
        String demo= restClient.demo();
        logger.info("Statement Received: {}", demo);
        return demo;
    }

}
