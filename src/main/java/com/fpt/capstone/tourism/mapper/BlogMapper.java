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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class, AuthorMapper.class})
public interface BlogMapper extends EntityMapper<BlogResponseDTO, Blog> {

    @Mapping(source = "author", target = "author")
    @Mapping(source = "tags", target = "blogTags")
    Blog toEntity(BlogRequestDTO dto);

    @Mapping(source = "author", target = "author")
    @Mapping(source = "blogTags", target = "tags")
    @Mapping(target = "deleted", source = "deleted")
    BlogResponseDTO toDTO(Blog entity);
}


