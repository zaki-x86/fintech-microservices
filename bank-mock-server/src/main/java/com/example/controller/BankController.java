package com.example.controller;


import com.example.dto.BankRequest;
import com.example.dto.BankResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
public class BankController {

    @PostMapping("/pay")
    @SneakyThrows
    public BankResponse pay(@RequestBody BankRequest request, HttpServletRequest httpRequest) {
        log.info("{} -> {}, body: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), request);

        if (request.getAmount().equals(BigDecimal.ONE)) {
            return null;
        }

        if(request.getAmount().equals(BigDecimal.valueOf(2))){
            throw new RuntimeException("Dummy exception!");
        }

        if (request.getAmount().equals(BigDecimal.TEN)) {
            Thread.sleep(Duration.ofMinutes(2).toMillis());
        }

        return BankResponse.builder().cardInfo(request.getCardInfo())
                .amount(request.getAmount())
                .transactionId(getTransactionId())
                .build();
    }

    private String getTransactionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
