package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.HomepageDTO;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.service.HomepageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class HomepageController {
    private final HomepageService homepageService;

    @GetMapping("/homepage")
    public ResponseEntity<GeneralResponse<HomepageDTO>> view(@RequestParam("numberTour") int numberTour,
                                                             @RequestParam("numberBlog") int numberBlog,
                                                             @RequestParam("numberActivity") int numberActivity) {
        return ResponseEntity.ok(homepageService.viewHomepage(numberTour, numberBlog, numberActivity));
    }
}
