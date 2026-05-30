package com.bookmyvenue.common.entity;

import com.bookmyvenue.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentGatewayId;

    private LocalDateTime createdAt;

    // 🔗 RELATIONSHIP

    // One payment belongs to one booking
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // 🔥 Auto set values
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = PaymentStatus.INITIATED;
        }
    }
}