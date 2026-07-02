package com.bookmyvenue.booking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long venueId;
    private Long userId;
    // private LocalDate startDate;
    // private LocalDate endDate;
    private String status;
    private double totalAmount;

    
}
