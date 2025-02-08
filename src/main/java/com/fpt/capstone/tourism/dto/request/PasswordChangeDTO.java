package com.fpt.capstone.tourism.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordChangeDTO {
    private String currentPassword;
    private String newPassword;
    private String newRePassword;
}
