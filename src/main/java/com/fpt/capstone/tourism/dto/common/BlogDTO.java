package com.fpt.capstone.tourism.dto.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BlogDTO {
    private Long id;
    private String thumbnailImageUrl;
    private String title;
    private String description;
    private String content;
    private AuthorDTO author;
    private List<TagDTO> tags;
    private boolean isDeleted;
    private LocalDateTime createdAt;
}
