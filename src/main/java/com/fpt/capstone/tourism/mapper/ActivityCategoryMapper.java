package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.ActivityCategoryDTO;
import com.fpt.capstone.tourism.model.ActivityCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityCategoryMapper extends EntityMapper<ActivityCategoryDTO, ActivityCategory> {
}
