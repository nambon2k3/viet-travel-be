package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.ActivityDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> findRecommendedActivities(int numberActivity);

    GeneralResponse<ActivityDTO> saveActivity(ActivityDTO activityDTO);

    GeneralResponse<ActivityDTO> getActivityById(Long id);

    GeneralResponse<PagingDTO<List<ActivityDTO>>> getAllActivity(int page, int size, String keyword, Boolean isDeleted, String orderDate, Long categoryId);

    GeneralResponse<ActivityDTO> updateActivity(Long id, ActivityDTO activityDTO);

    GeneralResponse<ActivityDTO> deleteActivity(Long id, boolean isDeleted);
}
