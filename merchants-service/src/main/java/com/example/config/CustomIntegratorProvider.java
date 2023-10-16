package com.example.config;

import com.example.model.dto.MerchantAttributeResponse;
import com.example.model.dto.MerchantResponse;
import com.example.model.dto.ServiceResponse;
import com.vladmihalcea.hibernate.type.util.ClassImportIntegrator;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        ClassImportIntegrator classImportIntegrator = new ClassImportIntegrator(
                List.of(ServiceResponse.class, MerchantResponse.class, MerchantAttributeResponse.class));
        return List.of(classImportIntegrator);
    }

}
