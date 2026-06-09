package com.bookmyvenue.venue.service;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import com.bookmyvenue.venue.dto.MyVenueDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VenueService {

    Page<Venue> getAllApprovedVenues(int page, int size, String city);

    Venue getVenueById(Long id);

    Venue createVenue(Venue venue);

    void approveVenue(Long id);

    void rejectVenue(Long id, String reason);

    // 🔥 Get ONLY logged-in owner's venues
    // ❌ No ownerId parameter → prevents security issues
    Page<MyVenueDto> getMyVenues(int page, int size);

    // 🗑️ Delete venue (later you should also check ownership here)

    void deleteVenue(Long id);

    Page<Venue> searchVenues(
            String name,
            String city,
            VenueStatus status,
            Pageable pageable);

    Page<Venue> getPendingVenues(int page, int size);

}