package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/marketing/blog")
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse<BlogDTO>> create(@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.saveBlog(blogDTO));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<GeneralResponse<BlogDTO>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse<BlogDTO>> update(@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.saveBlog(blogDTO));
    }


    @PostMapping("/change-status/{id}")
    public ResponseEntity<GeneralResponse<BlogDTO>> delete(@PathVariable Long id, @RequestParam boolean isDeleted) {
        return ResponseEntity.ok(blogService.changeBlogDeletedStatus(id, isDeleted));
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse<PagingDTO<List<BlogDTO>>>> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isDeleted) {
        return ResponseEntity.ok(blogService.getBlogs(page, size, keyword, isDeleted));
    }

}
