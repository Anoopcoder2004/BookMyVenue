package com.bookmyvenue.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class MyVenueDto {

    private Long id;
    private String name;
    private Integer capacity;
    private String status;
}