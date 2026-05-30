package com.bookmyvenue.venue.repository;

import com.bookmyvenue.common.entity.VenueImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueImageRepository extends JpaRepository<VenueImage, Long> {

    List<VenueImage> findByVenueId(Long venueId);
}