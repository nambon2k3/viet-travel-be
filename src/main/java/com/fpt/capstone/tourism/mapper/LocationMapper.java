package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.ServiceContact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper extends EntityMapper<LocationDTO, Location>{
}
