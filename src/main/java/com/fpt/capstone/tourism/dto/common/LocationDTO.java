package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.model.GeoPosition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private GeoPosition geoPosition;
}
