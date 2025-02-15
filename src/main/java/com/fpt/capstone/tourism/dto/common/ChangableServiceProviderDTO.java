package com.fpt.capstone.tourism.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangableServiceProviderDTO {
    private String imageUrl;
    private String abbreviation;
    private String website;
    private String email;
    private String phone;
    private String address;
    private LocationDTO location;
    private GeoPositionDTO geoPosition;
    private Set<ServiceCategoryDTO> serviceCategories;
}

