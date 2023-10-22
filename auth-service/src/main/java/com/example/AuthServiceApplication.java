package com.example;

import com.example.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@SpringBootApplication
public class AuthServiceApplication {


    private final JwtService jwtService;


    @GetMapping("/test")
    public boolean test(String token) {
        return jwtService.isTokenValid(token);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
