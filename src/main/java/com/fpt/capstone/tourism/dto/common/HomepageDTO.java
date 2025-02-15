package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.model.Activity;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.Tour;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HomepageDTO {
    private TourDTO topTourOfYear;
    private List<TourDTO> trendingTours;
    private List<BlogResponseDTO> newBlogs;
    private List<ActivityDTO> recommendedActivities;
}
