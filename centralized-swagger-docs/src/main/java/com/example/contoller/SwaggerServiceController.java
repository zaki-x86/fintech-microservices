package com.example.contoller;

import com.example.config.ServiceDefinitionContext;
import com.example.config.SwaggerDefinitionUpdater;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class SwaggerServiceController {

    private final ServiceDefinitionContext definitionContext;
    private final SwaggerDefinitionUpdater definitionUpdater;

    public SwaggerServiceController(ServiceDefinitionContext definitionContext, SwaggerDefinitionUpdater definitionUpdater) {
        this.definitionContext = definitionContext;
        this.definitionUpdater = definitionUpdater;
    }

    @GetMapping("/{serviceName}")
    public String getServiceDefinition(@PathVariable("serviceName") String serviceName) {
        return definitionContext.getSwaggerDefinition(serviceName);
    }

    @PutMapping
    public void update() {
        definitionUpdater.refresh();
    }
}
