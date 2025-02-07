package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;

public interface BlogService {
    GeneralResponse<BlogDTO> saveBlog(BlogDTO blogDTO);

    GeneralResponse<BlogDTO> getBlogById(Long id);

    GeneralResponse<BlogDTO> changeBlogDeletedStatus(Long id, boolean isDeleted);
}
