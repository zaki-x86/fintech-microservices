package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRequest {
    private long epochMillis;
    private String merchantName;
    private List<Attribute> attributes;
}
