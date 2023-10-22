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
    private JwtServiceGrpc.JwtServiceStub stub;


    @GetMapping
    public String get() {

        StringValue asdasd = StringValue.newBuilder().setValue("asdasd").build();

        System.out.println(stub.getChannel());
        stub.validateToken(asdasd, new StreamObserver<BoolValue>() {
            @Override
            public void onNext(BoolValue value) {
                System.out.println("onNext");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    return "OK";
    }
}
