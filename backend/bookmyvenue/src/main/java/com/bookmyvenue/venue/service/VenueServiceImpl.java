package com.bookmyvenue.venue.service;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import com.bookmyvenue.venue.repository.VenueRepository;
import com.bookmyvenue.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    // 🔹 USER: only see APPROVED venues
    @Override
    public Page<Venue> getAllApprovedVenues(int page, int size, String city) {

        if (city != null) {
            return venueRepository.findByCityAndStatus(
                    city,
                    VenueStatus.APPROVED,
                    PageRequest.of(page, size)
            );
        }

        return venueRepository.findByStatus(
                VenueStatus.APPROVED,
                PageRequest.of(page, size)
        );
    }

    @Override
    public Venue getVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
    }

    // 🔹 OWNER creates venue → status = PENDING
    @Override
    public Venue createVenue(Venue venue) {
        venue.setStatus(VenueStatus.PENDING);
        return venueRepository.save(venue);
    }

    // 🔹 OWNER: view their venues
    @Override
    public List<Venue> getVenuesByOwner(Long ownerId) {
        return venueRepository.findByOwnerId(ownerId);
    }

    @Override
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
        @Override
    public Page<Venue> searchVenues(
            String name,
            String city,
            VenueStatus status,
            Pageable pageable
    ) {
        return venueRepository.searchVenues(name, city, status, pageable);
    }
}