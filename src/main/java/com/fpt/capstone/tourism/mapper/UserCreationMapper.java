package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserCreationMapper extends EntityMapper<UserCreationRequestDTO, User> {
}
