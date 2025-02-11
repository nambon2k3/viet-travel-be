package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper extends EntityMapper<TagDTO, Tag>{
}
