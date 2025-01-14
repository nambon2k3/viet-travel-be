package com.fpt.capstone.tourism.dto;

import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
}
