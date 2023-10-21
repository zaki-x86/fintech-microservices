package com.example.service;

import com.example.exception.AuthenticationException;
import com.example.model.dto.LoginRequest;
import com.example.model.dto.LoginResponse;
import com.example.model.dto.UserAuthResponse;
import com.example.repository.UserClientRepo;
import com.example.repository.UserRepo;
import com.example.util.JwtIssuer;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final JwtIssuer jwt;
    private final UserClientRepo userClientRepo;
    private final HttpServletRequest request;

    public LoginResponse login(LoginRequest loginRequest) {

        UserAuthResponse userAuth = userRepo.findUserAuthByEmail(loginRequest.getEmail())
                .orElseThrow(AuthenticationException::new);

        checkPassword(loginRequest.getPassword(), userAuth.getPassword());
        saveUserClient(userAuth.getId());

        return LoginResponse.builder()
                .email(userAuth.getEmail())
                .token(jwt.issue(userAuth.getEmail()))
                .build();
    }


    private void checkPassword(String rawPassword, String hashedPassword) {
        if (!encoder.matches(rawPassword, hashedPassword))
            throw new AuthenticationException();
    }

    private void saveUserClient(long userId) {
        String header = request.getHeader(HttpHeaders.USER_AGENT);
        OperatingSystem os = UserAgent.parseUserAgentString(header).getOperatingSystem();

        userClientRepo.save(userId, os.getName(), request.getRemoteAddr());
    }

}
