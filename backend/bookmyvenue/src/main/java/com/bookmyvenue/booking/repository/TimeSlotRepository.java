package com.bookmyvenue.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookmyvenue.common.entity.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}