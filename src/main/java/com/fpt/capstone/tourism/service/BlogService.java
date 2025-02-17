package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.dto.request.BlogRequestDTO;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.PublicBlogResponseDTO;
import com.fpt.capstone.tourism.model.Blog;

import java.util.List;

public interface BlogService {
    GeneralResponse<BlogResponseDTO> createBlog(BlogRequestDTO blogRequestDTO);
    GeneralResponse<BlogResponseDTO> updateBlog(Long id,BlogRequestDTO blogRequestDTO);
    GeneralResponse<BlogResponseDTO> getBlogById(Long id);
    GeneralResponse<BlogResponseDTO> changeBlogDeletedStatus(Long id, boolean isDeleted);
    GeneralResponse<PagingDTO<List<BlogResponseDTO>>> getBlogs(int page, int size, String keyword, Boolean isDeleted);
    List<BlogResponseDTO> findNewestBlogs(int numberBlog);
    List<PublicBlogResponseDTO> getPublicBlog();
}
