package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.dto.response.PublicBlogResponseDTO;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicBlogMapper {
    @Mapping(source = "thumbnailImageUrl", target = "thumbnailImageUrl")
    @Mapping(source = "title", target = "title")
    @Mapping(target = "tags", source = "blogTags")
    PublicBlogResponseDTO blogToPublicBlogResponseDTO(Blog blog);

    default List<TagDTO> mapTagsToTagResponseDTO(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .toList();
    }
}
