package com.bookmyvenue.booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
// import java.nio.file.attribute.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bookmyvenue.booking.dto.BookingRequest;
import com.bookmyvenue.booking.dto.BookingResponse;
import com.bookmyvenue.booking.service.VenueBookingService;
import com.bookmyvenue.common.entity.Booking;
import com.bookmyvenue.config.CustomUserDetails;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final VenueBookingService bookingService;

    @PostMapping("path")
    public ResponseEntity <BookingResponse> createBooking(
        @RequestBody BookingRequest request,
        @AuthenticationPrincipal CustomUserDetails user
        ) {
            Long userId = user.getId();
            Booking booking = bookingService.createBooking(
                userId,
                request.getVenueId(),
                request.getStartDate(), //issue one
                request.getEndDate(),
                request.getTimeSlotId()
            );
        return ResponseEntity.ok(mapToResponse(booking));
    }    
    private BookingResponse mapToResponse(Booking booking) {
    return new BookingResponse(
            booking.getId(),
            booking.getVenue().getId(),
            booking.getUser().getId(),
            booking.getStatus().name(),
            booking.getTotalAmount()
    );
}
}
