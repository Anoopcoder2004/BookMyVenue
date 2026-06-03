package com.bookmyvenue.venue.repository;

import com.bookmyvenue.common.entity.Venue;
import com.bookmyvenue.common.enums.VenueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    Page<Venue> findByStatus(VenueStatus status, Pageable pageable);

    Page<Venue> findByCityAndStatus(String city, VenueStatus status, Pageable pageable);

    Page<Venue> findByOwnerId(Long ownerId,Pageable pageable);

    @Query("""
                SELECT v FROM Venue v
                WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%'))
                AND (:city IS NULL OR v.city = :city)
                AND (:status IS NULL OR v.status = :status)
            """)
    Page<Venue> searchVenues(
            @Param("name") String name,
            @Param("city") String city,
            @Param("status") VenueStatus status,
            Pageable pageable);
}