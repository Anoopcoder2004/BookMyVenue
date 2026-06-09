
package com.bookmyvenue.admin;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import com.bookmyvenue.venue.service.VenueService;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/admin/venues")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 🔥 PUT IT HERE

public class AdminController {

    private final VenueService venueService;

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveVenue(@PathVariable Long id) {
        venueService.approveVenue(id);
        return ResponseEntity.ok("Venue approved");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectVenue(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        venueService.rejectVenue(id, reason);

        return ResponseEntity.ok("Venue rejected");
    }

  @GetMapping("/pending")
public Page<Venue> getPendingVenues(
        @RequestParam int page,
        @RequestParam int size
) {
    System.out.println("📥 ADMIN PENDING REQUEST HIT");

    return venueService.getPendingVenues(page, size);
}
}
