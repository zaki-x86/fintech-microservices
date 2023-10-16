package com.example.service;

import com.example.exception.CustomNotFoundException;
import com.example.model.dto.MerchantAttributeResponse;
import com.example.model.dto.MerchantResponse;
import com.example.repository.MerchantAttributeRepository;
import com.example.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private MerchantAttributeRepository merchantAttributeRepository;
    private MerchantService merchantService;

    private MerchantResponse DUMMY_MERCHANT_RESPONSE;
    private MerchantAttributeResponse DUMMY_MERCHANT_ATTRIBUTE_RESPONSE;

    @BeforeEach
    void init() {
        merchantService = new MerchantService(merchantRepository, merchantAttributeRepository);

        DUMMY_MERCHANT_ATTRIBUTE_RESPONSE = MerchantAttributeResponse.builder()
                .id(1)
                .name("dummy-merchant-attribute")
                .build();

        DUMMY_MERCHANT_RESPONSE = MerchantResponse.builder()
                .id(1)
                .name("dummy-merchant")
                .icon("838383_dummy_merchant.svg")
                .rowNumber(1)
                .merchantAttributes(List.of(DUMMY_MERCHANT_ATTRIBUTE_RESPONSE, DUMMY_MERCHANT_ATTRIBUTE_RESPONSE))
                .build();
    }

    @Test
    public void testGetById() {
        int id = 1;
        when(merchantRepository.findById(id)).thenReturn(Optional.of(DUMMY_MERCHANT_RESPONSE));
        MerchantResponse merchantResponse = merchantService.get(id);

        assertTrue("Merchant Response is null", merchantResponse != null);
        assertEquals(merchantResponse.getId(), DUMMY_MERCHANT_RESPONSE.getId());
        assertEquals(merchantResponse.getName(), DUMMY_MERCHANT_RESPONSE.getName());
        assertEquals(merchantResponse.getIcon(), DUMMY_MERCHANT_RESPONSE.getIcon());
        assertEquals(merchantResponse.getRowNumber(), DUMMY_MERCHANT_RESPONSE.getRowNumber());
        assertEquals(merchantResponse.getMerchantAttributes().size(), DUMMY_MERCHANT_RESPONSE.getMerchantAttributes().size());
    }

    @Test
    public void testThrowsExceptionIfIdNotFoundInGetById() {
        int id = 4;
        when(merchantRepository.findById(id)).thenThrow(CustomNotFoundException.class);
        assertThatThrownBy(() -> merchantService.get(id))
                .isInstanceOf(CustomNotFoundException.class);
    }
}
