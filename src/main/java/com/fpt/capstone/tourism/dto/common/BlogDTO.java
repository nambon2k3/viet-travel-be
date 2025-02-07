package com.fpt.capstone.tourism.dto.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlogDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Long authorId;
    private List<Long> tagIds;
    private boolean isDeleted;
}
