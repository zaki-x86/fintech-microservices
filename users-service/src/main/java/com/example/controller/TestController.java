package com.example.controller;

import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.JwtServiceGrpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GrpcClient("auth-service")
    private JwtServiceGrpc.JwtServiceBlockingStub stub2;

    @GetMapping
    public String get(String token) {


        StringValue asdasd = StringValue.newBuilder().setValue(token).build();

        BoolValue boolValue = stub2.isTokenValid(asdasd);
        System.out.println(boolValue.getValue());

        return "OK";
    }
}
