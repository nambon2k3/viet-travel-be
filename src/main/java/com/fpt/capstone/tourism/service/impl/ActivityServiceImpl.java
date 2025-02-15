package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.ActivityDTO;
import com.fpt.capstone.tourism.mapper.ActivityMapper;
import com.fpt.capstone.tourism.model.Activity;
import com.fpt.capstone.tourism.repository.ActivityRepository;
import com.fpt.capstone.tourism.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public List<ActivityDTO> findRecommendedActivities(int numberActivity) {
        List<Activity> randomActivities = activityRepository.findRandomActivities(numberActivity);
        return randomActivities.stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }
}
