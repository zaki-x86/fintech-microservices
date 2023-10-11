package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String firstName;
    private String lastname;
    private String email;
    private String status;
    private LocalDate birthDate;
    @JsonFormat(pattern = "MMM. d, yyyy HH:mm")
    private LocalDateTime createdAt;
}
