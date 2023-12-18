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

@Data
@Slf4j
@RequiredArgsConstructor
public class BankClientProxy {


    private final BankClient client;


    public BankResponse pay(BankRequest request) {

        try {
            // convert String and log
            // research if proxy is practical way to wrap client request
            ResponseEntity<BankResponse> re = client.pay(request);

            if (re.getStatusCode().is2xxSuccessful()) {
                BankResponse response = re.getBody();
                if (response == null) {
                    throw new RuntimeException("Success response from bankClient is null ");
                }
                response.setBankStatus(BankStatus.OK);
                return response;
            } else {
                return BankResponse.ERROR_RESPONSE;
            }
        } catch (RetryableException ex) {
            return BankResponse.TIMEOUT_RESPONSE;
        } catch (Exception ex) {
            log.error("Exception while sending request to bank, ", ex);
        }

        return BankResponse.WAITING_RESPONSE;
    }

}
