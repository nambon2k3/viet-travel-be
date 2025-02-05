package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.response.UserManageGeneralInformationDTO;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserManageGeneralInformationMapper extends EntityMapper<UserManageGeneralInformationDTO, User> {
}