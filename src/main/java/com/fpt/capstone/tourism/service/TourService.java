package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.TourDTO;

import java.util.List;

public interface TourService {
    TourDTO findTopTourOfYear();

    List<TourDTO> findTrendingTours(int numberTour);

}
