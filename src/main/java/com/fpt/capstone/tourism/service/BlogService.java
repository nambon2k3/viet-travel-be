package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;

import java.util.List;

public interface BlogService {
    GeneralResponse<BlogDTO> saveBlog(BlogDTO blogDTO);

    GeneralResponse<BlogDTO> getBlogById(Long id);

    GeneralResponse<BlogDTO> changeBlogDeletedStatus(Long id, boolean isDeleted);

    public GeneralResponse<PagingDTO<List<BlogDTO>>> getBlogs(int page, int size, String keyword, Boolean isDeleted);

}
