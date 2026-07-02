package com.bookmyvenue.booking.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class BookingRequest {

    private Long venueId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long timeSlotId;
    
}
