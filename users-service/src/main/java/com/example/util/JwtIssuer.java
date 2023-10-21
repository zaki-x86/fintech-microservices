package com.example.util;


import com.example.config.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final SecurityProperties properties;

    public String issue(String email) {
        Duration duration = Duration.ofMinutes(properties.getJwt().getExpirationWithMinutes());
        return Jwts.builder()
                .setSubject(properties.getJwt().getSubject())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(duration)))
                .claim(SecurityProperties.CLAIM_USER_EMAIL, email)
                .signWith(properties.getJwt().getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

}
