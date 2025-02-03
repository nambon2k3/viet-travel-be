package com.fpt.capstone.tourism.dto.response;


import com.fpt.capstone.tourism.enums.Gender;

import com.fpt.capstone.tourism.enums.RoleName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Gender gender;
    private String phone;
    private String address;
    private String avatarImg;
    private RoleName role;

}
