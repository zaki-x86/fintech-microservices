package com.example.service;

import com.example.proxy.BankClientProxy;
import com.example.util.JsonConvertor;
import com.example.client.BankClient;
import com.example.entity.Payment;
import com.example.model.dto.BankRequest;
import com.example.model.dto.BankResponse;
import com.example.model.dto.PaymentRequest;
import com.example.model.dto.PaymentResponse;
import com.example.model.enumeration.BankStatus;
import com.example.repo.PaymentRepo;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final BankClientProxy bankProxy;
    private final BankClient client;
    private final JsonConvertor convertor;


    public PaymentResponse pay(PaymentRequest request, String userEmail) {

        Payment payment = preInitialize(request, userEmail);

        BankRequest bankRequest = BankRequest.builder().amount(request.getAmount())
                .cardInfo(request.getCardInfo())
                .build();

        payment.setBankRequest(convertor.toJson(bankRequest));


        BankResponse bankResponse = bankProxy.pay(bankRequest);
        payment.setBankResponse(convertor.toJson(bankResponse));
        payment.setTransactionId(bankResponse.getTransactionId());
        payment.setBankStatus(bankResponse.getBankStatus());


        // TODO: save
        // TODO: mail/notification request

        return fillResponse(payment);
    }

    private PaymentResponse fillResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        boolean success = BankStatus.success(payment.getBankStatus());
        response.setSuccess(success);
        response.setAmount(payment.getAmount());
        response.setMerchant(payment.getMerchant());
        response.setTransactionId(payment.getTransactionId());

        return response;
    }

    private Payment preInitialize(PaymentRequest request, String userEmail) {
        Payment payment = new Payment();

        payment.setMerchant(request.getMerchant());
        payment.setValues(request.getValues());
        payment.setBankStatus(BankStatus.WAITING);
        payment.setAmount(request.getAmount());
        payment.setUserEmail(userEmail);

        return payment;
    }


}
