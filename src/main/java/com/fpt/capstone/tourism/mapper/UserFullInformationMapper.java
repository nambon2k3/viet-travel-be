package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserFullInformationMapper extends EntityMapper<UserFullInformationResponseDTO, User> {
}
