package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.repository.TagRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marketing/blog")
public class BlogController {


    private final BlogService blogService;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final BlogMapper blogMapper;

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

}
