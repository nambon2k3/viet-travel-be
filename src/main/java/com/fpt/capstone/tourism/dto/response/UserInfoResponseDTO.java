package com.fpt.capstone.tourism.dto.response;

import com.fpt.capstone.tourism.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Role role;
}
