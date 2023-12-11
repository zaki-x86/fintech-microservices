package com.example.entity;


import com.example.model.enumeration.BankStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String transactionId;
    private String values;
    private String userEmail;
    private String merchant;
    private String bankRequest;
    private String bankResponse;
    @Enumerated(EnumType.STRING)
    private BankStatus bankStatusStatus;
    @Column(scale = 2)
    private BigDecimal amount;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;

}
