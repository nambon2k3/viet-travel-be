package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.model.Tour;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TourScheduleDTO {
    private Long id;
    private Date date;
    private Boolean deleted;
}
