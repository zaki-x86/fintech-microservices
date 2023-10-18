package com.example.entity;


import com.example.model.enumeration.Os;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ip;

    @Enumerated(EnumType.STRING)
    private Os os;

    private int userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
