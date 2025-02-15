package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.ActivityDTO;
import com.fpt.capstone.tourism.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity>  {
}
