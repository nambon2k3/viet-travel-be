package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;

import java.util.List;

public interface BlogMapper extends EntityMapper<BlogDTO, Blog>{
    Blog toEntity(BlogDTO dto, User author, List<Tag> tags);
}
