package com.bookmyvenue.venue.controller;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import com.bookmyvenue.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

  @GetMapping
public Page<Venue> getVenues(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String city
) {
        System.out.println("🔥 CONTROLLER HIT");

    return venueService.getAllApprovedVenues(page, size, city);
}

    // 🔹 2. Get single venue by ID
    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    // 🔹 3. Create new venue (OWNER)
    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.createVenue(venue);
    }

    // 🔹 4. Update venue
    // @PutMapping("/{id}")
    // public Venue updateVenue(
    //         @PathVariable Long id,
    //         @RequestBody Venue venue
    // ) {
    //     return venueService.updateVenue(id, venue);
    // }

    // 🔹 5. Delete venue
    @DeleteMapping("/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "Venue deleted successfully";
    }

@GetMapping("/search")
public Page<Venue> searchVenues(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) VenueStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
) {
    Pageable pageable = PageRequest.of(page, size);
    
    return venueService.searchVenues(name, city, status, pageable);
}
}