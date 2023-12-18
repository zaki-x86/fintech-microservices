package com.example.model.dto;

import com.example.model.enumeration.BankStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {

    public static final BankResponse TIMEOUT_RESPONSE = BankResponse.builder().bankStatus(BankStatus.TIMEOUT).build();
    public static final BankResponse ERROR_RESPONSE = BankResponse.builder().bankStatus(BankStatus.ERROR).build();
    public static final BankResponse WAITING_RESPONSE = BankResponse.builder().bankStatus(BankStatus.WAITING).build();


    private CardInfo cardInfo;
    private BigDecimal amount;
    private String transactionId;

    private BankStatus bankStatus;
}
