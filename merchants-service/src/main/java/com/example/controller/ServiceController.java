package com.example.controller;


import com.example.model.dto.ServiceResponse;
import com.example.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        var serviceResponses = serviceService.getAll();
        return ResponseEntity.ok(serviceResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> get(@PathVariable Integer id) {
        var serviceResponse = serviceService.get(id);
        return ResponseEntity.ok(serviceResponse);
    }

}
