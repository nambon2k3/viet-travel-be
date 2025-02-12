package com.fpt.capstone.tourism.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetDTO {
    private String password;
    private String rePassword;
}
