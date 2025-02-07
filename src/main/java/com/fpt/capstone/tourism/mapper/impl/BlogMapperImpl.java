package com.fpt.capstone.tourism.mapper.impl;

import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogMapperImpl implements BlogMapper {

    @Override
    public BlogDTO toDTO(Blog entity) {
        return BlogDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .content(entity.getContent())
                .authorId(entity.getAuthor().getId())
                .tagIds(entity.getBlogTags().stream().map(Tag::getId) // Extract the ID from each Tag
                        .collect(Collectors.toList()))
                .isDeleted(entity.isDeleted())
                .build();
    }

    @Override
    public Blog toEntity(BlogDTO dto) {
        return null;
    }

    @Override
    public Blog toEntity(BlogDTO dto, User author, List<Tag> tags) {
        return Blog.builder()
                .id(dto.getId() == 0 ? null : dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .content(dto.getContent())
                .author(author) // Set User object
                .blogTags(tags) // Set List<Tag> object
                .isDeleted(dto.isDeleted())
                .build();
    }
}
