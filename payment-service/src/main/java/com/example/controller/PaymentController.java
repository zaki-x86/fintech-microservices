package com.example.controller;

import com.example.model.dto.PaymentRequest;
import com.example.model.dto.PaymentResponse;
import com.example.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse pay(@RequestBody PaymentRequest request, @RequestHeader("X_USER_EMAIL") String userEmail){
        return paymentService.pay(request,userEmail);
    }


}
