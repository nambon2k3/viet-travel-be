package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.enums.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private RoleName role;
}
