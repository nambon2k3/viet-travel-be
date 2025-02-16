package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.dto.response.PagingDTO;

import java.util.Date;
import java.util.List;

public interface HomepageService {
    GeneralResponse<HomepageDTO> viewHomepage(int numberTour, int numberBlog, int numberActivity);


    GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> viewAllHotel(int page, int size, String keyword);

    GeneralResponse<PagingDTO<List<ServiceProviderDTO>>> viewAllRestaurant(int page, int size, String keyword);

    GeneralResponse<PagingDTO<List<TourDTO>>> viewAllTour(int page, int size, String keyword, Double budgetFrom, Double budgetTo, Integer duration, Date fromDate);
}
