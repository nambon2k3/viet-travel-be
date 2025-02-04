package com.fpt.capstone.tourism.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String rePassword;
    private String email;
    private String fullName;
    private String phone;
    private String address;
}