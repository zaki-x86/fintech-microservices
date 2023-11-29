package com.example.service;


import com.example.entity.Service;
import com.example.exception.CustomNotFoundException;
import com.example.model.dto.MerchantResponse;
import com.example.model.dto.ServiceResponse;
import com.example.repository.MerchantRepository;
import com.example.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final MerchantRepository merchantRepository;

    public List<ServiceResponse> getAll()  {
        Sort sort = Sort.by("rowNumber");
        return serviceRepository.findAll2(sort);
    }

    public ServiceResponse get(Integer serviceId) {
        ServiceResponse service = serviceRepository.findById2(serviceId)
                .orElseThrow(() -> new CustomNotFoundException(Service.class.getSimpleName(),serviceId));
        List<MerchantResponse> merchants = merchantRepository.findAllByService(serviceId, Sort.by("rowNumber"));
        service.setMerchantResponses(merchants);
        return service;
    }
}
