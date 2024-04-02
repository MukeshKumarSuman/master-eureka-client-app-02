package com.nps.feignclient;

import com.nps.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "app-03", url = "http://localhost:1111", configuration = PaymentFeignConfig.class)
public interface PaymentClient {
    @PostMapping("/payment")
    void initializePayment( PaymentRequest request);

}
