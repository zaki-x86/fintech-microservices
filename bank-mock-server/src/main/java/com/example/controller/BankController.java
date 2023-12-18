package com.example.controller;


import com.example.dto.BankResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
public class BankController {

    @PostMapping("/pay")
    public BankResponse pay(@RequestBody BankResponse request, HttpServletRequest httpRequest) {
        log.info("{} -> {}, body: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), request);

        return BankResponse.builder().amount(request.getAmount())
                .amount(request.getAmount())
                .transactionId(getTransactionId())
                .build();
    }

    private String getTransactionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
