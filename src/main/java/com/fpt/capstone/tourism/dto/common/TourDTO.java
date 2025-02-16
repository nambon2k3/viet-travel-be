package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class TourDTO {
    private Long id;
    private String name;
    private String highlights;
    private int numberSeats;
    private int numberDays;
    private int numberNight;
    private String note;
    private List<Long> locationsId;
    private List<Long> tagsId;
    private Long departLocationId;
}
