package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.HomepageDTO;

public interface HomepageService {
    GeneralResponse<HomepageDTO> viewHomepage(int numberTour, int numberBlog, int numberActivity);
}
