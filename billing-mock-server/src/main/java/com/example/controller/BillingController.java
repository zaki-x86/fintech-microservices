package com.example.controller;


import com.example.dto.CheckRequest;
import com.example.dto.CheckResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Random;

@RestController
public class BillingController {


    @GetMapping("/check")
    public CheckResponse check(CheckRequest request) {

        var response = CheckResponse.builder()
                .transactionId(getTrId(request))
                .epochMillis(Instant.EPOCH.toEpochMilli())
                .merchantName(request.getMerchantName())
                .attributes(request.getAttributes())
                .build();

        return response;
    }


    private String getTrId(CheckRequest request) {
        Random random = new Random();
        int number = random.nextInt(100);
        return String.format("%s-%s", request.getMerchantName(), number);
    }


    @GetMapping("/pay")
    public String pay() {
        return "Ok";
    }


}
