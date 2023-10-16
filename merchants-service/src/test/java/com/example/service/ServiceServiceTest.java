package com.example.service;

import com.example.exception.CustomNotFoundException;
import com.example.model.dto.MerchantResponse;
import com.example.model.dto.ServiceResponse;
import com.example.repository.MerchantRepository;
import com.example.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private MerchantRepository merchantRepository;
    private ServiceService serviceService;

    private ServiceResponse DUMMY_SERVICE_RESPONSE;
    private MerchantResponse DUMMY_MERCHANT_RESPONSE;

    @BeforeEach
    void init() {
        serviceService = new ServiceService(serviceRepository, merchantRepository);

        DUMMY_MERCHANT_RESPONSE = MerchantResponse.builder()
                .id(1)
                .name("dummy-merchant")
                .icon("838383_dummy_merchant.svg")
                .rowNumber(1)
                .build();


        DUMMY_SERVICE_RESPONSE = ServiceResponse.builder()
                .id(2)
                .name("dummy-service")
                .icon("1234534_dummy_service.svg")
                .rowNumber(2)
                .merchantResponses(List.of(DUMMY_MERCHANT_RESPONSE, DUMMY_MERCHANT_RESPONSE))
                .build();
    }


    @Test
    public void testGetById() {
        int id = 1;
        when(serviceRepository.findById2(id)).thenReturn(Optional.of(DUMMY_SERVICE_RESPONSE));
        ServiceResponse serviceResponse = serviceService.get(id);

        assertTrue("Service Response is null", serviceResponse != null);
        assertEquals(serviceResponse.getId(), DUMMY_SERVICE_RESPONSE.getId());
        assertEquals(serviceResponse.getName(), DUMMY_SERVICE_RESPONSE.getName());
        assertEquals(serviceResponse.getIcon(), DUMMY_SERVICE_RESPONSE.getIcon());
        assertEquals(serviceResponse.getRowNumber(), DUMMY_SERVICE_RESPONSE.getRowNumber());
        assertEquals(serviceResponse.getMerchantResponses().size(), DUMMY_SERVICE_RESPONSE.getMerchantResponses().size());
    }

    @Test
    public void testThrowsExceptionIfIdNotFoundInGetById() {
        int id = 4;
        when(serviceRepository.findById2(id)).thenThrow(CustomNotFoundException.class);
        assertThatThrownBy(() -> serviceService.get(id))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    public void testGetAll() {
        List<ServiceResponse> dummyServiceResponses = List.of(DUMMY_SERVICE_RESPONSE, DUMMY_SERVICE_RESPONSE, DUMMY_SERVICE_RESPONSE);
        when(serviceRepository.findAll2(Sort.by("rowNumber"))).thenReturn(dummyServiceResponses);
        List<ServiceResponse> serviceResponses =serviceService.getAll();
        assertEquals(serviceResponses.size(), dummyServiceResponses.size());
    }
}
