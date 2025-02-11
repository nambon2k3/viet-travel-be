package com.fpt.capstone.tourism.dto.request;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequestDTO {
    private String fullName;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
}
