package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PublicBlogResponseDTO;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("public/blog")
public class PublicBlogController {
    private final BlogService blogService;

//    @GetMapping("")
//    public ResponseEntity<List<PublicBlogResponseDTO>> getPublic() {
//        List<PublicBlogResponseDTO> publicBlog = blogService.getPublicBlog();
//        return ResponseEntity.ok(publicBlog);
//    }

    @GetMapping("/food-and-drinks")
    public ResponseEntity<List<PublicBlogResponseDTO>> getFoodAndDrinkBlogs(
            @RequestParam(defaultValue = "3") int numberOfBlogs) {
        List<PublicBlogResponseDTO> foodAndDrinkBlogs = blogService.getBlogsByTagName("Food & Drinks", numberOfBlogs);
        return ResponseEntity.ok(foodAndDrinkBlogs);
    }

    @GetMapping("/adventure")
    public ResponseEntity<List<PublicBlogResponseDTO>> getAdventureBlogs(
            @RequestParam(defaultValue = "6") int numberOfBlogs) {
        List<PublicBlogResponseDTO> adventureBlogs = blogService.getBlogsByTagName("Adventure", numberOfBlogs);
        return ResponseEntity.ok(adventureBlogs);
    }

    @GetMapping("/cultural")
    public ResponseEntity<List<PublicBlogResponseDTO>> getCulturalBlogs(
            @RequestParam(defaultValue = "3") int numberOfBlogs) {
        List<PublicBlogResponseDTO> culturalBlogs = blogService.getBlogsByTagName("Cultural", numberOfBlogs);
        return ResponseEntity.ok(culturalBlogs);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<PublicBlogResponseDTO>> getNewestBlogs(
            @RequestParam(defaultValue = "11") int numberOfBlogs) {
        List<PublicBlogResponseDTO> newestBlogs = blogService.getNewestBlogs(numberOfBlogs);
        return ResponseEntity.ok(newestBlogs);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<GeneralResponse<BlogResponseDTO>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
}
