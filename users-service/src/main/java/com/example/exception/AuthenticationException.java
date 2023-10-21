package com.example.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private final String message;

    public AuthenticationException() {
        this.message = "Username or password is wrong";
    }
}
