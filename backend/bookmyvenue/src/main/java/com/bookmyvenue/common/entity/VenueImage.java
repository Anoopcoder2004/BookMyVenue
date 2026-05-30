package com.bookmyvenue.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venue_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    // 🔗 RELATIONSHIP

    // Many images belong to one venue
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}