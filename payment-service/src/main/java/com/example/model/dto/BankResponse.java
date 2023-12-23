package com.example.model.dto;

import com.example.model.enumeration.BankStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    //TODO: analyze heap behaviour with J monition tools while using this objects
    public static final BankResponse TIMEOUT_RESPONSE = BankResponse.builder().bankStatus(BankStatus.TIMEOUT).build();
    public static final BankResponse ERROR_RESPONSE = BankResponse.builder().bankStatus(BankStatus.ERROR).build();
    public static final BankResponse WAITING_RESPONSE = BankResponse.builder().bankStatus(BankStatus.WAITING).build();


    private CardInfo cardInfo;
    private BigDecimal amount;
    private String transactionId;

    @ToString.Exclude
    @JsonIgnore
    private BankStatus bankStatus;
}
