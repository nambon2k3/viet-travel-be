package com.fpt.capstone.tourism.dto.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityCategoryDTO {
    private Long id;
    private String name;
    private boolean isDeleted;
}
