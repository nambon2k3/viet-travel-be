package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.ServiceContactDTO;
import com.fpt.capstone.tourism.model.ServiceContact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceContactMapper extends EntityMapper<ServiceContactDTO, ServiceContact> {
}
