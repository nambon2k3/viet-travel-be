package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.ServiceContactManagementRequestDTO;
import com.fpt.capstone.tourism.dto.response.ServiceContactManagementResponseDTO;
import com.fpt.capstone.tourism.model.ServiceContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceContactMapper extends EntityMapper<ServiceContactManagementRequestDTO, ServiceContact> {
    ServiceContact toEntity(ServiceContactManagementRequestDTO dto);

    @Mapping(source = "serviceProvider.name", target = "serviceProviderName")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ServiceContactManagementResponseDTO toResponseDTO(ServiceContact entity);
}
