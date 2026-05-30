package com.bookmyvenue.booking.repository;

import com.bookmyvenue.common.entity.Booking;
import com.bookmyvenue.common.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByVenueId(Long venueId);

    // 🔥 CRITICAL: Check availability
    boolean existsByVenueIdAndBookingDateAndStatusIn(
            Long venueId,
            LocalDate bookingDate,
            List<BookingStatus> statuses
    );
}