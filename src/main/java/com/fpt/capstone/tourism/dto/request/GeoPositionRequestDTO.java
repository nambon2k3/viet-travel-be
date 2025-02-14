package com.fpt.capstone.tourism.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class GeoPositionRequestDTO {
    private Double latitude;
    private Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPositionRequestDTO that = (GeoPositionRequestDTO) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
