package com.fpt.capstone.tourism.dto.request;

import lombok.Data;

@Data
public class LocationRequestDTO {
    private String name;
    private String description;
    private String image;
    private GeoPositionRequestDTO geoPosition;
}
