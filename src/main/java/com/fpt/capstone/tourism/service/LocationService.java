package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationCreateDTO;

public interface LocationService {
    GeneralResponse<LocationDTO> saveLocation(LocationCreateDTO locationCreateDTO);

    GeneralResponse<LocationDTO> getLocationById(Long id);
}
