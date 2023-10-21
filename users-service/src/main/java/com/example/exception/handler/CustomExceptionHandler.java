package com.example.exception.handler;

import com.example.exception.AlreadyExistsException;
import com.example.exception.AuthenticationException;
import com.example.model.dto.ExceptionResponse;
import com.example.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<?> handleCustomNotFoundException(CustomNotFoundException exception,
                                                                 WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }



    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException exception,
                                                                 WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
