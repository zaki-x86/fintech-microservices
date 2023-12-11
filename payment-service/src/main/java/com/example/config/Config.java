package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Config {


    @Bean
    public ObjectWriter objectMapper() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
}
