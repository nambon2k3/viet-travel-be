package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserManageGeneralInformationResponseDTO {
    private String fullName;
    private String username;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
    private List<String> roles;
}
