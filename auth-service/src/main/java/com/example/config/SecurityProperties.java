package com.example.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.security.Key;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    public static final String CLAIM_USER_ID = "userId";
    public static final String BEARER = "Bearer";

    private final JwtProperties jwt = new JwtProperties();
    private final CorsConfiguration cors = new CorsConfiguration();

    @Data
    public static class JwtProperties {
        private String subject;
        private String secretKey;
        private long expirationWithMinutes;

        public Key getKey() {
            return Keys.hmacShaKeyFor(secretKey.getBytes());
        }
    }
}
