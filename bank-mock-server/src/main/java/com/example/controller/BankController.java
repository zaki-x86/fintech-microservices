package com.example.controller;


import com.example.dto.BankResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@RestController
public class BankController {

    @PostMapping("/pay")
    public BankResponse pay(@RequestBody BankResponse request) {

        var response = BankResponse.builder().amount(request.getAmount())
                .amount(request.getAmount())
                .transactionId(getTransactionId())
                .build();

        return response;
    }

    private String getTransactionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
