package com.fpt.capstone.tourism.mapper.impl;

import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.mapper.AuthorMapper;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.mapper.TagMapper;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlogMapperImpl implements BlogMapper {

    private final TagMapper tagMapper;
    private final AuthorMapper authorMapper;

    @Override
    public BlogDTO toDTO(Blog entity) {
        return BlogDTO.builder()
                .id(entity.getId())
                .thumbnailImageUrl(entity.getThumbnailImageUrl())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .content(entity.getContent())
                .author(authorMapper.toDTO(entity.getAuthor()))
                .tags(entity.getBlogTags().stream()
                        .map(tagMapper::toDTO)
                        .collect(Collectors.toList()))
                .isDeleted(entity.isDeleted())
                .createdAt(entity.getCreatedAt())
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
                .thumbnailImageUrl(dto.getThumbnailImageUrl())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .content(dto.getContent())
                .author(author) // Set User object
                .blogTags(tags) // Set List<Tag> object
                .isDeleted(dto.isDeleted())
                .build();
    }
}
