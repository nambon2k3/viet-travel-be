package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.dto.common.GeoPositionDTO;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDTO {
    private Long id;

    private String imageUrl;

    @NotBlank(message = "Abbreviation is required")
    private String abbreviation;

    private String website;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String address;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocationDTO location;

    private GeoPositionDTO geoPosition;

    private Set<ServiceCategoryDTO> serviceCategories;
}

