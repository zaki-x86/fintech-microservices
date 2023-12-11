package com.example.service;

import com.example.util.JsonConvertor;
import com.example.client.BankClient;
import com.example.entity.Payment;
import com.example.model.dto.BankRequest;
import com.example.model.dto.BankResponse;
import com.example.model.dto.PaymentRequest;
import com.example.model.dto.PaymentResponse;
import com.example.model.enumeration.BankStatus;
import com.example.repo.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepo repo;
    private final BankClient client;
    private final JsonConvertor convertor;


    public PaymentResponse pay(PaymentRequest request, String userEmail) {

        Payment payment = new Payment();

        payment.setMerchant(request.getMerchant());
        payment.setValues(request.getValues());
        payment.setBankStatusStatus(BankStatus.WAITING);
        payment.setAmount(request.getAmount());
        payment.setUserEmail(userEmail);

        BankRequest bankRequest = BankRequest.builder().amount(request.getAmount())
                .cardInfo(request.getCardInfo())
                .build();

        payment.setBankRequest(convertor.toJson(bankRequest));


        try {
            ResponseEntity<BankResponse> re = client.pay(bankRequest);

            if (re.getStatusCode().is2xxSuccessful()) {
                BankResponse response = re.getBody();
                payment.setBankResponse(convertor.toJson(response));
                payment.setTransactionId(response.getTransactionId());
                payment.setBankStatusStatus(BankStatus.OK);
            } else {

            }

        } catch (Exception exception) {

        }
        return null;

    }


}
