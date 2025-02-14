package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.request.BlogRequestDTO;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlogMapper extends EntityMapper<BlogResponseDTO, Blog> {

    @Mapping(target = "blogTags", source = "tags")
    Blog toEntity(BlogRequestDTO dto, User author, List<Tag> tags);

    @Mapping(target = "tags", source = "blogTags")
    BlogResponseDTO toDTO(Blog blog);
}
