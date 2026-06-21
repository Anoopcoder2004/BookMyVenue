package com.bookmyvenue.venue.service;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.entity.User;
import com.bookmyvenue.user.repository.UserRepository;
import com.bookmyvenue.common.enums.VenueStatus;
import com.bookmyvenue.venue.dto.MyVenueDto;
import com.bookmyvenue.venue.repository.VenueRepository;
import com.bookmyvenue.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

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

    // 🔐 Get logged-in user from JWT
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    Long userId = Long.parseLong(auth.getName());

    User user = userRepository.findById(userId).orElseThrow();

    // 🔥 Assign owner automatically
    venue.setOwner(user);

    // Optional: set default status
    venue.setStatus(VenueStatus.PENDING);

    return venueRepository.save(venue);
}

    // 🔹 OWNER: view their venues
   @Override
public Page<MyVenueDto> getMyVenues(int page, int size) {

    // 🔐 Get logged-in user
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    Long userId = Long.parseLong(auth.getName());

    Page<Venue> venues = venueRepository.findByOwnerId(
        userId, PageRequest.of(page, size)
        );

    // 🔥 Only fetch this user's venues
  return venues.map(v -> new MyVenueDto(
    v.getId(),
    v.getName(),
    v.getCapacity(),
    v.getStatus().name(),
    v.getAddress(),
    v.getPricePerDay(),
    v.getDescription(),
    v.getImages()
        .stream()
        .map(img -> img.getImageUrl()) 
        .toList()
));
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
    @Override
public void approveVenue(Long id) {
    Venue venue = venueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venue not found"));

    venue.setStatus(VenueStatus.APPROVED);
    venue.setRejectionReason(null); // clear if previously rejected

    venueRepository.save(venue);
}
@Override
public void rejectVenue(Long id, String reason) {
    Venue venue = venueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venue not found"));

    venue.setStatus(VenueStatus.REJECTED);
    venue.setRejectionReason(reason);

    venueRepository.save(venue);
}

@Override
public Page<Venue> getPendingVenues(int page, int size) {

    return venueRepository.findByStatus(
            VenueStatus.PENDING,
            PageRequest.of(page, size)
    );
}
}