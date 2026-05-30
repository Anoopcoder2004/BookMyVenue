package com.bookmyvenue.common.entity;

import com.bookmyvenue.common.enums.VenueStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String city;

    private String address;

    private Double pricePerDay;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private VenueStatus status;

    private LocalDateTime createdAt;

    // 🔗 RELATIONSHIPS

    // Many venues belong to one user (owner)
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // One venue can have many images
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<VenueImage> images;

    // One venue can have many bookings
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;

    // 🔥 Auto set createdAt
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        // default status when created
        if (this.status == null) {
            this.status = VenueStatus.PENDING;
        }
    }
}