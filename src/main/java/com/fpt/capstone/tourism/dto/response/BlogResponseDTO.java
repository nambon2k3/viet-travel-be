package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.dto.common.AuthorDTO;
import com.fpt.capstone.tourism.dto.common.TagDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BlogResponseDTO {
    private Long id;
    private String thumbnailImageUrl;
    private String title;
    private String description;
    private String content;
    private AuthorDTO author;
    private List<TagDTO> tags;
    private Boolean deleted;
    private LocalDateTime createdAt;
}
