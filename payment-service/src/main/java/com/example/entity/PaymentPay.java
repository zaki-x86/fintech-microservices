package com.example.entity;


import com.example.model.enumeration.BillingStatus;
import com.example.model.enumeration.ProcessingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PaymentPay {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String processingId;
    private String processingRequest;
    private String processingResponse;
    @Enumerated(EnumType.STRING)
    private ProcessingStatus billingCheckStatus;

    private String billingPayRequest;
    private String billingPayResponse;
    @Enumerated(EnumType.STRING)
    private BillingStatus billingPayStatus;

    @Column(scale = 2)
    private BigDecimal amount;
    private boolean isNotificationSent;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "payment_check_id", referencedColumnName = "id")
    private PaymentCheck paymentCheck;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;
}
