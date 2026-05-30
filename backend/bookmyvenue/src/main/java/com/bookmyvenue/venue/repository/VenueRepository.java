package com.bookmyvenue.venue.repository;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByStatus(VenueStatus status);

    List<Venue> findByCityAndStatus(String city, VenueStatus status);

    List<Venue> findByOwnerId(Long ownerId);
}