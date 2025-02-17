package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PublicBlogResponseDTO;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("public/blog")
public class PublicBlogController {
    private final BlogService blogService;

    @GetMapping("")
    public ResponseEntity<List<PublicBlogResponseDTO>> getPublic() {
        List<PublicBlogResponseDTO> publicBlog = blogService.getPublicBlog();
        return ResponseEntity.ok(publicBlog);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<GeneralResponse<BlogResponseDTO>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
}
