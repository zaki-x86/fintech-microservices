package com.example.filter;

import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.JwtServiceGrpc;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Slf4j
@Component
public class AuthFilter implements GlobalFilter {

    public static final String X_USER_EMAIL = "X-User-Email";

    @GrpcClient("auth-service")
    private JwtServiceGrpc.JwtServiceBlockingStub jwtServiceBlockingStub;

    private String getUserEmail(String header) {
        StringValue headerStrVal = StringValue.of(header);
        BoolValue boolVal = jwtServiceBlockingStub.isTokenValid(headerStrVal);
        if (!boolVal.getValue())
            throw new RuntimeException(); //TODO
        else {
            StringValue emailStrVal = jwtServiceBlockingStub.getUserEmail(headerStrVal);
            return emailStrVal.getValue();
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> { //TODO: exclude Urls

                    Optional.ofNullable(httpHeaders.get(HttpHeaders.AUTHORIZATION)).stream()
                            .findFirst()
                            .map(a -> a.get(0))
                            .map(this::getUserEmail)
                            .ifPresent(s -> httpHeaders.set(X_USER_EMAIL, s));

                }).build();

        return chain.filter(exchange.mutate().request(request).build());
    }
}