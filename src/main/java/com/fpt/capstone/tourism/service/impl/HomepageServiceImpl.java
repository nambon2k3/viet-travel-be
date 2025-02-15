package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.ActivityMapper;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.mapper.TourMapper;
import com.fpt.capstone.tourism.model.Activity;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.Tour;
import com.fpt.capstone.tourism.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomepageServiceImpl implements HomepageService {
    private final TourService tourService;
    private final BlogService blogService;
    private final ActivityService activityService;
    private final ServiceProviderService providerService;
    private final TourMapper tourMapper;
    private final ActivityMapper activityMapper;
    private final BlogMapper blogMapper;

    @Override
    public GeneralResponse<HomepageDTO> viewHomepage(int numberTour, int numberBlog, int numberActivity) {
        TourDTO topTourOfYear = tourService.findTopTourOfYear();
        List<TourDTO> trendingTours = tourService.findTrendingTours(numberTour);
        List<BlogResponseDTO> newBlogs = blogService.findNewestBlogs(numberBlog);
        List<ActivityDTO> recomendedActivities = activityService.findRecommendedActivities(numberActivity);

        //Mapping to Dto


        HomepageDTO homepageDTO = HomepageDTO.builder()
                .topTourOfYear(topTourOfYear)
                .newBlogs(newBlogs)
                .trendingTours(trendingTours)
                .recommendedActivities(recomendedActivities)
                .build();

        return new GeneralResponse<>(HttpStatus.OK.value(), "Homepage loaded successfully", homepageDTO);
    }

    @Override
    public GeneralResponse<PagingDTO<List<TourDTO>>> viewAllTour(int page, int size, String keyword, Boolean isDeleted) {
        return null;
    }

    @Override
    public GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> viewAllHotel(int page, int size, String keyword) {
        return providerService.getAllHotel(page, size, keyword);
    }

    @Override
    public GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> viewAllRestaurant(int page, int size, String keyword) {
        return providerService.getAllRestaurant(page, size, keyword);
    }
}
