package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterInforResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Gender gender;
    private String phone;
    private String address;
}
