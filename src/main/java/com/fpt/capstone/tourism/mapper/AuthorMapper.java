package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.common.AuthorDTO;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper extends EntityMapper<AuthorDTO, User> {
}
