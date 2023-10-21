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
    private final JwtIssuer issuer;
    private final UserClientRepo userClientRepo;
    private final HttpServletRequest request;

    public LoginResponse login(LoginRequest loginRequest) {

        validateLoginRequest(loginRequest);
        saveUserClient();

        return LoginResponse.builder()
                .email(loginRequest.getEmail())
                .token(issuer.issue(loginRequest.getEmail()))
                .build();
    }

    private void saveUserClient() {
        String header = request.getHeader(HttpHeaders.USER_AGENT);
        OperatingSystem os = UserAgent.parseUserAgentString(header).getOperatingSystem();
        String ip = request.getRemoteAddr();

        userClientRepo.save(0, os.getName(), ip);
    }


    private void validateLoginRequest(LoginRequest request) {

        UserAuthResponse userAuth = userRepo.findUserAuthByEmail(request.getEmail())
                .orElseThrow(AuthenticationException::new);

        if (!encoder.matches(request.getPassword(), userAuth.getPassword()))
            throw new AuthenticationException();
    }

}
