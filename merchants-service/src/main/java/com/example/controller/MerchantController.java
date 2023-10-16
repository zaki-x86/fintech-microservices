package com.example.controller;


import com.example.model.dto.MerchantResponse;
import com.example.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/{id}")
    public ResponseEntity<MerchantResponse> get(@PathVariable int id) {
        var merchantResponse = merchantService.get(id);
        return ResponseEntity.ok(merchantResponse);
    }


}
