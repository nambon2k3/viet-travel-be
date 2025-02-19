package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.model.ActivityCategory;
import com.fpt.capstone.tourism.model.GeoPosition;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.TourDayActivity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ActivityDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private double pricePerPerson;
    private boolean isDeleted;
    private GeoPositionDTO geoPosition;
    private LocationDTO locationId;
    private Long activityCategoryId;
}
