package com.example.controller;


import com.example.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TempController {


    private final JwtService jwtService;

    @GetMapping
    public String get() {
        String token = jwtService.issue(1);
        return token;
    }

    @GetMapping("2")
    public int get2(String header) {
        return jwtService.getUserId(header);
    }

}
