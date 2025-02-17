package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.dto.common.TagDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PublicBlogResponseDTO {
    private Long id;
    private String thumbnailImageUrl;
    private String title;
    private List<TagDTO> tags;
}
