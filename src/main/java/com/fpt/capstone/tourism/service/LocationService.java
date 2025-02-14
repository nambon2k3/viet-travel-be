package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationRequestDTO;

public interface LocationService {
    GeneralResponse<LocationDTO> saveLocation(LocationRequestDTO locationRequestDTO);

    GeneralResponse<LocationDTO> getLocationById(Long id);
}
