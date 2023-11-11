package com.example.entity;


import com.example.model.dto.Value;
import com.example.model.enumeration.BillingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transactionId;

//    @Type(type = "jsonb") //TODO
//    @Column(columnDefinition = "jsonb")
//    private Value values;

    private String userEmail;
    private String merchantName;
    private String billingCheckRequest;
    private String billingCheckResponse;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingCheckStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;

}
