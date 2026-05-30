package com.bookmyvenue.common.entity;

import com.bookmyvenue.common.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookingDate;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime createdAt;

    // 🔗 RELATIONSHIPS

    // Many bookings belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many bookings belong to one venue
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    // 🔥 Auto set values before insert
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }
}