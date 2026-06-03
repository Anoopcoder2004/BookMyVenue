package com.bookmyvenue.venue.service;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VenueService {

    Page<Venue> getAllApprovedVenues(int page, int size, String city);

    Venue getVenueById(Long id);

    Venue createVenue(Venue venue);

    List<Venue> getVenuesByOwner(Long ownerId);

    void deleteVenue(Long id);
        Page<Venue> searchVenues(
            String name,
            String city,
            VenueStatus status,
            Pageable pageable
    );


}