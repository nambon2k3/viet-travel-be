package com.fpt.capstone.tourism.converter;

import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.model.User;

public class Converter {
    public static UserInfoResponseDTO convertUseToUserResponseDTO(User user){
         return UserInfoResponseDTO.builder()
                 .id(user.getId())
                 .username(user.getUsername())
                 .fullName(user.getFullName())
                 .phone(user.getPhone())
                 .address(user.getAddress())
                 .role(user.getRole())
                 .gender(user.getGender())
                 .email(user.getEmail())
                 .build();
    }
}
