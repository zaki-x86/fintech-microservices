package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@RestController
public class ZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }

    @GetMapping("/test")
    public String get() throws InterruptedException {
        System.out.println("as");
        Thread.sleep(1000);

        return "OK";
    }

}
