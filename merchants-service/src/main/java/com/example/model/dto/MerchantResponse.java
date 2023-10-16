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
public class MerchantResponse {
    private int id;
    private String name;
    private String icon;
    private int rowNumber;

    private List<MerchantAttributeResponse> merchantAttributes;

    public MerchantResponse(int id, String name, String icon, int rowNumber) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.rowNumber = rowNumber;
    }
}
