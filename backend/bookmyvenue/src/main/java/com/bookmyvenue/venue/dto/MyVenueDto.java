package com.bookmyvenue.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter 
@AllArgsConstructor
public class MyVenueDto {

    private Long id;
    private String name;
    private Integer capacity;
    private String status;
    private String address;
    private Double pricePerDay;
    private String description;
    private List<String> imageUrls;

}