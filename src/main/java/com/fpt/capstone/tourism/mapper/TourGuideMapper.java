package com.fpt.capstone.tourism.mapper;

import com.fpt.capstone.tourism.dto.request.TourGuideRequestDTO;
import com.fpt.capstone.tourism.dto.response.TourGuideResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TourGuideMapper extends EntityMapper<TourGuideResponseDTO, User>{
    User toEntity(TourGuideRequestDTO dto);

    @Mapping(target = "deleted", source = "deleted")
    TourGuideResponseDTO toDTO(User entity);
}
