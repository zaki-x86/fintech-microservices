package com.example.service;

import com.example.config.SecurityProperties;
import com.example.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityProperties properties;

    public int getUserId(String header) {
        return Optional.ofNullable(header)
                .filter(this::isTokenBearer)
                .flatMap(this::toUserId)
                .orElseThrow(AuthenticationException::new);
    }

    private boolean isTokenBearer(String header) {
        return header.startsWith(SecurityProperties.BEARER);
    }

    private Optional<Integer> toUserId(String header) {
        String token = header.substring(SecurityProperties.BEARER.length()).trim();
        Claims claims = parse(token);

        if (isTokenExpired(claims))
            return Optional.empty();


        int userId = (int) claims.get(SecurityProperties.CLAIM_USER_ID);
        return Optional.of(userId);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(properties.getJwt().getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String issue(int userId) {
        Duration duration = Duration.ofMinutes(properties.getJwt().getExpirationWithMinutes());
        return Jwts.builder()
                .setSubject(properties.getJwt().getSubject())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(duration)))
                .claim(SecurityProperties.CLAIM_USER_ID, userId)
                .signWith(properties.getJwt().getKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
