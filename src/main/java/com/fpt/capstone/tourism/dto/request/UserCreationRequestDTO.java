package com.fpt.capstone.tourism.dto.request;

import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.RoleName;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequestDTO {
    private String fullName;
    private String username;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
    private RoleName role;
    private String avatarImage;
    private boolean isDeleted;
}
