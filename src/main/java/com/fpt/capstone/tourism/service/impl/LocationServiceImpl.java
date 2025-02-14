package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.request.LocationRequestDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.LocationMapper;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.repository.LocationRepository;
import com.fpt.capstone.tourism.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    @Override
    public GeneralResponse<LocationDTO> saveLocation(LocationRequestDTO locationRequestDTO) {
        try{
            //Validate input data
            Validator.validateLocation(locationRequestDTO);

            //Check duplicate location
            if(locationRepository.findByName(locationRequestDTO.getName()) != null){
                throw BusinessException.of(EXISTED_LOCATION);
            }

            //Save date to database
//            Location location = locationMapper.toEntity(locationDTO);
//            location.setCreatedAt(LocalDateTime.now());
//            location.setDeleted(false);
//            locationRepository.save(location);

//            return new GeneralResponse<>(HttpStatus.OK.value(), CREATE_LOCATION_SUCCESS, locationDTO);
        }catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(CREATE_LOCATION_FAIL, ex);
        }
        return null;
    }

    @Override
    public GeneralResponse<LocationDTO> getLocationById(Long id) {
        return null;
    }
}
