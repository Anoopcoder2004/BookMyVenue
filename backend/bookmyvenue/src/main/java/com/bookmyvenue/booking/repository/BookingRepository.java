package com.bookmyvenue.booking.repository;

import com.bookmyvenue.common.entity.Booking;
import com.bookmyvenue.common.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByVenueId(Long venueId);

 boolean existsByVenueIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusIn(
        Long venueId,
        LocalDate endDate,
        LocalDate startDate,
        List<BookingStatus> statuses
);
boolean existsByTimeSlotIdAndStatusIn(
        Long timeSlotId,
        List<BookingStatus> statuses
);
}