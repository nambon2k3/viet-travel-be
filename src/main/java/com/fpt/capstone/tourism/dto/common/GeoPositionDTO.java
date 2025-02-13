package com.fpt.capstone.tourism.dto.common;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GeoPositionDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private boolean isDeleted;
}
