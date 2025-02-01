package com.fpt.capstone.tourism.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServiceCategoryDTO {
    private Long id;
    private String categoryName;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

