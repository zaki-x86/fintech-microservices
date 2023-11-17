package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckResponse {
    private String transactionId;
    private long epochMillis;
    private String merchantName;
    private List<Attribute> attributes;
}
