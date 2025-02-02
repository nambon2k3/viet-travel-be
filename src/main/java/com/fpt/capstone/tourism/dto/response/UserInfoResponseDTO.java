package com.fpt.capstone.tourism.dto.response;

<<<<<<< HEAD
import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.Role;
=======
import com.fpt.capstone.tourism.enums.RoleName;
>>>>>>> develop
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
<<<<<<< HEAD
    private Gender gender;
    private String phone;
    private String address;
    private String avatarImg;
    private Role role;
=======
    private RoleName role;
>>>>>>> develop
}
