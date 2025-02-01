package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import com.fpt.capstone.tourism.model.ServiceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper(componentModel = "spring")
@Primary
public interface ServiceCategoryMapper {
    ServiceCategoryMapper INSTANCE = Mappers.getMapper(ServiceCategoryMapper.class);

    ServiceCategoryDTO toDTO(ServiceCategory serviceCategory);
    ServiceCategory toEntity(ServiceCategoryDTO serviceCategoryDTO);
}

