package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationCreateDTO;
import com.fpt.capstone.tourism.service.LocationService;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    @Override
    public GeneralResponse<LocationDTO> saveLocation(LocationCreateDTO locationCreateDTO) {
        return null;
    }

    @Override
    public GeneralResponse<LocationDTO> getLocationById(Long id) {
        return null;
    }
}
