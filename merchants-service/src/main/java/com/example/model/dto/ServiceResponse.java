package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {

    private int id;
    private String name;
    private String icon;
    private String rowNumber;
    private List<MerchantResponse> merchantResponses;

    public ServiceResponse(int id, String name, String icon, String rowNumber) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.rowNumber = rowNumber;
    }
}
