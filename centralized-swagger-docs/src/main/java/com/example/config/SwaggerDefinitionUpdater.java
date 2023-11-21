package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class SwaggerDefinitionUpdater {
    public static final String X_USER_EMAIL = "X-User-Email";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";
    private static final String KEY_SWAGGER_URL = "swagger_url";
    public static final String SWAGGER_HOST_PATTERN = "localhost:+[0-9]{4}";
    public static final String API_GATEWAY = "api-gateway";
    @Value("${spring.application.name}")
    private String currentSwaggerModule;
    private final RestTemplate template;
    private final DiscoveryClient discoveryClient;
    private final ServiceDefinitionContext definitionContext;

    public SwaggerDefinitionUpdater(DiscoveryClient discoveryClient, ServiceDefinitionContext definitionContext) {
        this.discoveryClient = discoveryClient;
        this.definitionContext = definitionContext;
        this.template = new RestTemplate();
    }

    public void refresh() {

        String gatewayAddress = findGatewayAddressFromDiscovery();

        discoveryClient.getServices().stream()
                .filter(s -> !s.equals(currentSwaggerModule))
                .forEach(serviceId -> {

                    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
                    if (CollectionUtils.isEmpty(serviceInstances)) {
                        logger.info("No instances available for service : {} ", serviceId);
                    } else {
                        ServiceInstance instance = serviceInstances.get(0);
                        String swaggerURL = getSwaggerURL(instance);

                        Optional<String> jsonData = getSwaggerDefinitionForAPI(serviceId, swaggerURL);
                        if (jsonData.isPresent()) {
                            String serviceDescription = jsonData.get()
                                    .replaceAll(X_USER_EMAIL, Config.TOKEN_PREFIX)
                                    .replaceAll(SWAGGER_HOST_PATTERN, gatewayAddress);
                            definitionContext.addServiceDefinition(serviceId, serviceDescription);
                        } else {
                            logger.error("Skipping service id : {} Error : Could not get Swagger definition from API ", serviceId);
                        }

                        logger.info("Service Definition Context Refreshed at :  {}", LocalDate.now());
                    }
                });
    }

    private String findGatewayAddressFromDiscovery() {
        List<ServiceInstance> apiGatewayInstances = discoveryClient.getInstances(API_GATEWAY);
        if (CollectionUtils.isEmpty(apiGatewayInstances)) {
            logger.error("Gateway Instances is not up");
            System.exit(-1);
        }
        ServiceInstance apiGatewayInstance = apiGatewayInstances.get(0);
        return String.format("%s:%s", apiGatewayInstance.getHost(), apiGatewayInstance.getPort());
    }

    private String getSwaggerURL(ServiceInstance instance) {
        String swaggerURL = instance.getMetadata().get(KEY_SWAGGER_URL);
        return swaggerURL != null ? instance.getUri() + swaggerURL : instance.getUri() + DEFAULT_SWAGGER_URL;
    }

    private Optional<String> getSwaggerDefinitionForAPI(String serviceName, String url) {
        logger.debug("Accessing the SwaggerDefinition JSON for Service : {} : URL : {} ", serviceName, url);
        try {
            String jsonData = template.getForObject(url, String.class);
            return Optional.of(jsonData);
        } catch (RestClientException ex) {
            logger.error("Error while getting service definition for service : {} Error : {} ", serviceName, ex.getMessage());
            return Optional.empty();
        }

    }


}
