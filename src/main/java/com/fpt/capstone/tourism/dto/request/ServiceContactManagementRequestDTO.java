package com.fpt.capstone.tourism.dto.request;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceContactManagementRequestDTO {
    private String position;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
}

