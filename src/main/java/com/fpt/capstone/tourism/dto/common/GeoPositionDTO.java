package com.fpt.capstone.tourism.dto.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class GeoPositionDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private boolean isDeleted;
}
