package com.example.proxy;

import com.example.client.BankClient;
import com.example.model.dto.BankRequest;
import com.example.model.dto.BankResponse;
import com.example.model.enumeration.BankStatus;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Data
@Slf4j
@RequiredArgsConstructor
public class BankClientProxy {


    private final BankClient client;


    //TODO: if client send another response except 200 e.g [{"timestamp":"2023-05-23T10:15:28.780+00:00","status":500,"error":"Internal Server Error","path":"/pay"}],
    // return it and write it to db somehow
    //TODO: add retry logic to bankClient and add ratelimiter to bankside
    public BankResponse pay(BankRequest request) {
        try {
            log.debug("Request to bank,  body = {}", request);
            ResponseEntity<BankResponse> re = client.pay(request);

            if (re.getStatusCode().is2xxSuccessful()) {
                BankResponse response = re.getBody();
                if (response == null) {
                    log.error("Response from bank is null");
                    return BankResponse.ERROR_RESPONSE;
                }
                log.debug("Response from bank, body = {}", response);
                response.setBankStatus(BankStatus.OK);
                return response;
            } else {
                return BankResponse.ERROR_RESPONSE;
            }
        } catch (RetryableException ex) {
            log.error("Timeout in bank request {}", ex.getMessage());
            return BankResponse.TIMEOUT_RESPONSE;
        } catch (Exception ex) {
            log.error("Exception in bank request ", ex);
        }

        return BankResponse.WAITING_RESPONSE;
    }

}
