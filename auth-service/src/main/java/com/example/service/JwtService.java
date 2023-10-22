package com.example.service;

import com.example.config.SecurityProperties;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.JwtServiceGrpc;

import java.util.Date;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class JwtService extends JwtServiceGrpc.JwtServiceImplBase {

    private final SecurityProperties properties;

    @Override
    public void validateToken(StringValue request, StreamObserver<BoolValue> responseObserver) {

        BoolValue boolValue = BoolValue.newBuilder()
                .setValue(isTokenValid(request.getValue()))
                .build();

        responseObserver.onNext(boolValue);
        responseObserver.onCompleted();
    }

    public boolean isTokenValid(String header) {
        return Optional.ofNullable(header)
                .filter(this::isTokenBearer)
                .filter(this::isTokenNotExpired)
                .isPresent();
    }

    private boolean isTokenBearer(String header) {
        return header.startsWith(SecurityProperties.BEARER);
    }


    private boolean isTokenNotExpired(String header) {
        String token = header.substring(SecurityProperties.BEARER.length()).trim();
        Claims claims = parse(token);

        return claims.getExpiration().before(new Date());
    }

    private Claims parse(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(properties.getJwt().getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public void getUserEmail(StringValue request, StreamObserver<StringValue> responseObserver) {

        StringValue stringValue = StringValue.newBuilder()
                .setValue(getUserEmail(request.getValue()))
                .build();

        responseObserver.onNext(stringValue);
        responseObserver.onCompleted();
    }

    public String getUserEmail(String header) {
        String token = header.substring(SecurityProperties.BEARER.length()).trim();
        Claims claims = parse(token);

        return String.valueOf(claims.get(SecurityProperties.CLAIM_USER_EMAIL));
    }
}
