package com.bookmyvenue.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "time_slots")

public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean booked;
    private double price;
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
    
}
