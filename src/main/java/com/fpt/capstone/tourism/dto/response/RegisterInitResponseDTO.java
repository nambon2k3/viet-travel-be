package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterInitResponseDTO {
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Gender gender;
    private String confirmationToken;
}
