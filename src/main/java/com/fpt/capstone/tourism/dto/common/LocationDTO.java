package com.fpt.capstone.tourism.dto.common;


import com.fpt.capstone.tourism.model.GeoPosition;
import lombok.Data;


@Data
public class LocationDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean deleted;
    private GeoPosition geoPosition;

}
