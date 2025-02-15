package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.service.HomepageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list-tour")
    public ResponseEntity<GeneralResponse<PagingDTO<List<TourDTO>>>> viewAllTour(@RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                 @RequestParam(required = false) String keyword,
                                                                                 @RequestParam(required = false) Boolean isDeleted){
        return ResponseEntity.ok(homepageService.viewAllTour(page, size, keyword, isDeleted));
    }
    @GetMapping("/list-hotel")
    public ResponseEntity<GeneralResponse<PagingDTO<List<ServiceProviderDTO>>>> viewAllHotel(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = "10") int size,
                                                                                             @RequestParam(required = false) String keyword){
        return ResponseEntity.ok(homepageService.viewAllHotel(page, size, keyword));
    }
    @GetMapping("/list-restaurant")
    public ResponseEntity<GeneralResponse<PagingDTO<List<ServiceProviderDTO>>>> viewAllRestaurant(@RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                 @RequestParam(required = false) String keyword){
        return ResponseEntity.ok(homepageService.viewAllRestaurant(page, size, keyword));
    }
}
