package com.nps.feignclient;

import com.nps.dto.PaymentRequest;
import com.nps.dto.StatementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(name = "app-03", url = "http://localhost:2222/v1", configuration = PaymentFeignConfig.class)
@FeignClient(name = "app-03", configuration = PaymentFeignConfig.class)
public interface PaymentClient {
    @PostMapping("/payment")
    void initializePayment( PaymentRequest request);


    @GetMapping(value = "/statement", consumes = "application/json")
    StatementResponse getStatement();

    @GetMapping("/demo")
    String demo();
}
