package com.fpt.capstone.tourism.dto.request;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterConfirmRequestDTO {
        private String username;
        private String email;
        private String fullName;
        private String password;
        private Gender gender;
        private String phone;
        private String address;
}
