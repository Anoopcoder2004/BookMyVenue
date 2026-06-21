// package com.bookmyvenue.common.entity;

// @Entity
// @Table(name = "venue_status_history")
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class VenueStatusHistory {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     // which venue
//     @ManyToOne
//     @JoinColumn(name = "venue_id")
//     private Venue venue;

//     // status change (APPROVED / REJECTED)
//     @Enumerated(EnumType.STRING)
//     private VenueStatus status;

//     // who did it (admin)
//     @ManyToOne
//     @JoinColumn(name = "admin_id")
//     private User admin;

//     // when it happened
//     private LocalDateTime actionTime;

//     // optional reason (for rejection)
//     private String reason;

//     @PrePersist
//     public void prePersist() {
//         this.actionTime = LocalDateTime.now();
//     }
// }