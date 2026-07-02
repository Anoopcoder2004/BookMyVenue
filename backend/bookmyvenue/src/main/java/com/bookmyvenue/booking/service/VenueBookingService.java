package com.bookmyvenue.booking.service;

import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.bookmyvenue.booking.repository.BookingRepository;
import com.bookmyvenue.venue.repository.VenueRepository;
import com.bookmyvenue.booking.repository.TimeSlotRepository;
import com.bookmyvenue.common.enums.BookingStatus;
import com.bookmyvenue.common.entity.Booking;
import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.entity.User;
import com.bookmyvenue.common.entity.TimeSlot;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueBookingService {

    private final VenueRepository venueRepository;
    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;

    public Booking createBooking(
            Long userId,
            Long venueId,
            LocalDate startDate,
            LocalDate endDate,
            Long timeSlotId) {

        Venue venue = findVenueOrThrow(venueId);
        User user = createUserReference(userId);

        // if (isDailyBooking(venue)) {
        //     return createDailyBooking(user, venue, startDate, endDate);
        // }

        return createHourlyBooking(user, venue, timeSlotId);
    }

    // ===================== CORE HELPERS =====================

    // private Venue findVenueOrThrow(Long venueId) {
    //     return venueRepository.findById(venueId)
    //             .orElseThrow(() -> new VenueNotFoundException(venueId));
    // }debug method

    private Venue findVenueOrThrow(Long venueId) {

    System.out.println("Searching for venue with ID: " + venueId);

    var venue = venueRepository.findById(venueId);

    if (venue.isPresent()) {
        System.out.println("Venue found: " + venue.get().getName());
        return venue.get();
    }

    System.out.println("Venue NOT found with ID: " + venueId);
    throw new VenueNotFoundException(venueId);
}

    private User createUserReference(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    // private boolean isDailyBooking(Venue venue) {
    //     return venue.getBookingType() == BookingType.DAILY;
    // }

    // ===================== DAILY FLOW =====================

    private Booking createDailyBooking(User user, Venue venue,
            LocalDate startDate, LocalDate endDate) {

        validateDates(startDate, endDate);
        ensureNoDateConflict(venue.getId(), startDate, endDate);

        Booking booking = buildDailyBooking(user, venue, startDate, endDate);
        return bookingRepository.save(booking);
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new InvalidBookingException("Start and End date required");
        }
    }

    private void ensureNoDateConflict(Long venueId, LocalDate start, LocalDate end) {
        boolean exists = bookingRepository
                .existsByVenueIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusIn(
                        venueId,
                        end,
                        start,
                        List.of(BookingStatus.CONFIRMED, BookingStatus.PENDING));

        if (exists) {
            throw new BookingConflictException("Venue already booked for these dates");
        }
    }

    private Booking buildDailyBooking(User user, Venue venue,
            LocalDate startDate, LocalDate endDate) {

        return Booking.builder()
                .venue(venue)
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .status(BookingStatus.CONFIRMED)
                .totalAmount(calculateDailyAmount(venue, startDate, endDate))
                .build();
    }

    private double calculateDailyAmount(Venue venue,
            LocalDate startDate,
            LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return days * venue.getPricePerDay(); // assuming this field exists
    }

    // ===================== HOURLY FLOW =====================

    private Booking createHourlyBooking(User user, Venue venue, Long timeSlotId) {

        validateTimeSlot(timeSlotId);

        TimeSlot slot = findSlotOrThrow(timeSlotId);
        ensureSlotAvailable(slot);
        lockSlot(slot);

        Booking booking = buildHourlyBooking(user, venue, slot);
        return bookingRepository.save(booking);
    }

    private void validateTimeSlot(Long timeSlotId) {
        if (timeSlotId == null) {
            throw new InvalidBookingException("TimeSlot required");
        }
    }

    private TimeSlot findSlotOrThrow(Long timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new TimeSlotNotFoundException(timeSlotId));
    }

    private void ensureSlotAvailable(TimeSlot slot) {
        if (slot.isBooked()) {
            throw new SlotAlreadyBookedException("Slot already booked");
        }
    }

    private void lockSlot(TimeSlot slot) {
        slot.setBooked(true);
        timeSlotRepository.save(slot);
    }

    private Booking buildHourlyBooking(User user, Venue venue, TimeSlot slot) {

        return Booking.builder()
                .venue(venue)
                .user(user)
                .timeSlot(slot)
                .status(BookingStatus.CONFIRMED)
                .totalAmount(calculateHourlyAmount(slot))
                .build();
    }

    private double calculateHourlyAmount(TimeSlot slot) {
        return slot.getPrice(); // assuming this field exists
    }

    public class VenueNotFoundException extends RuntimeException {
        public VenueNotFoundException(Long id) {
            super("Venue not found: " + id);
        }
    }

    public class TimeSlotNotFoundException extends RuntimeException {
        public TimeSlotNotFoundException(Long id) {
            super("TimeSlot not found: " + id);
        }
    }

    public class InvalidBookingException extends RuntimeException {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    public class BookingConflictException extends RuntimeException {
        public BookingConflictException(String message) {
            super(message);
        }
    }

    public class SlotAlreadyBookedException extends RuntimeException {
        public SlotAlreadyBookedException(String message) {
            super(message);
        }
    }

}
