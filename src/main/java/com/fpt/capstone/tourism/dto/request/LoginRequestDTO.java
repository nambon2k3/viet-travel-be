package com.fpt.capstone.tourism.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
    private boolean rememberMe;
}


