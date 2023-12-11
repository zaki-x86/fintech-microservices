package com.example.client;

import com.example.model.dto.BankRequest;
import com.example.model.dto.BankResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bank", url = "http://localhost:8585")
public interface BankClient {

    @PostMapping("/pay")
    ResponseEntity<BankResponse> pay(@RequestBody BankRequest request);

}
