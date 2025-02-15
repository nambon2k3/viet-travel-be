package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.ActivityDTO;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> findRecommendedActivities(int numberActivity);
}
