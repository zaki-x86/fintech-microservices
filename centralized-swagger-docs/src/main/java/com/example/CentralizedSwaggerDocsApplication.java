package com.example;

import com.example.config.SwaggerDefinitionUpdater;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class CentralizedSwaggerDocsApplication implements CommandLineRunner {

    private final SwaggerDefinitionUpdater swaggerDefinitionUpdater;

    public CentralizedSwaggerDocsApplication(SwaggerDefinitionUpdater swaggerDefinitionUpdater) {
        this.swaggerDefinitionUpdater = swaggerDefinitionUpdater;
    }


    public static void main(String[] args) {
        SpringApplication.run(CentralizedSwaggerDocsApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        swaggerDefinitionUpdater.refresh();
    }
}
