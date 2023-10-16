package com.example.service;

import com.example.entity.Merchant;
import com.example.exception.CustomNotFoundException;
import com.example.model.dto.MerchantAttributeResponse;
import com.example.model.dto.MerchantResponse;
import com.example.repository.MerchantAttributeRepository;
import com.example.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantAttributeRepository merchantAttributeRepository;

    public MerchantResponse get(int merchantId) {
        MerchantResponse merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new CustomNotFoundException(Merchant.class.getSimpleName(), merchantId));

        List<MerchantAttributeResponse> merchantAttributes = merchantAttributeRepository.findAllByMerchant(merchantId);
        merchant.setMerchantAttributes(merchantAttributes);

        return merchant;
    }
}
