package com.exampl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@EnableSwagger2
@SpringBootApplication
public class CentralizedSwaggerDocsApplication implements CommandLineRunner {

    private final ConcurrentHashMap<String, String> serviceDescriptions = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        SpringApplication.run(CentralizedSwaggerDocsApplication.class, args);
    }


    @GetMapping("/service/{servicename}")
    public String getServiceDefinition(@PathVariable("servicename") String serviceName) {
        System.out.println("serviceNAme " + serviceName);
        return serviceDescriptions.get(serviceName);
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            SwaggerResource resource = new SwaggerResource();
            resource.setLocation("/service/test");
            resource.setName("test");
            resource.setSwaggerVersion("3.0");

            resources.add(resource);
            return resources;
        };
    }


    @Override
    public void run(String... args) throws Exception {
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8082/v2/api-docs", String.class);
        String body = forEntity.getBody();
        System.out.println("Body  = " + body);
        serviceDescriptions.put("test", body);

    }
}
