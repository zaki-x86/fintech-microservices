package com.example.config;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionContext {

    private final ConcurrentHashMap<String, String> serviceDescriptions;

    private ServiceDefinitionContext() {
        serviceDescriptions = new ConcurrentHashMap<>();
    }

    public void addServiceDefinition(String serviceName, String serviceDescription) {
        serviceDescriptions.put(serviceName, serviceDescription);
    }

    public String getSwaggerDefinition(String serviceId) {
        return serviceDescriptions.get(serviceId);
    }

    public List<SwaggerResource> getSwaggerDefinitions() {
        return serviceDescriptions.keySet().stream().map(s -> {
            SwaggerResource resource = new SwaggerResource();
            resource.setLocation("/services/" + s);
            resource.setName(s);
            resource.setSwaggerVersion("3.0");
            return resource;
        }).collect(Collectors.toList());
    }

}
