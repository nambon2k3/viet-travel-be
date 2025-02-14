package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.GeoPositionDTO;
import com.fpt.capstone.tourism.model.GeoPosition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeoPositionMapper extends EntityMapper<GeoPositionDTO, GeoPosition> {
}
