package com.example.filter;

import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.JwtServiceGrpc;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        Optional<String> authHeaderOpt = Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION))
                .stream().findFirst()
                .map(a -> a.get(0));
        if (authHeaderOpt.isPresent()) {
            StringValue headerStrVal = StringValue.of(authHeaderOpt.get());
            BoolValue isTokenValid = jwtServiceBlockingStub.isTokenValid(headerStrVal);

            if (!isTokenValid.getValue())
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            else {
                StringValue emailStrVal = jwtServiceBlockingStub.getUserEmail(headerStrVal);
                request = request.mutate()
                        .header(X_USER_EMAIL, emailStrVal.getValue())
                        .build();
            }
        }

        return chain.filter(exchange.mutate().request(request).build());
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }
}