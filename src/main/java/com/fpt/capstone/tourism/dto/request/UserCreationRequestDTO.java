package com.fpt.capstone.tourism.dto.request;

import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.RoleName;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequestDTO {
    private String fullName;
    private String username;
    private String password;
    private String rePassword;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
    private String avatarImage;
    private List<String> roleNames;
}
