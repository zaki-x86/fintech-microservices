package com.example.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException{

    private final String message;

    public AlreadyExistsException(Object param) {
        this.message = String.format("Record with '%s' was already existed", param);
    }
}
