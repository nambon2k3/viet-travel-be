package com.fpt.capstone.tourism.dto.common;


import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean isDeleted;
    private Long geoPositionId;
}
