package com.fpt.capstone.tourism.dto.common;

import com.fpt.capstone.tourism.enums.Gender;
import lombok.*;

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
    private String serviceProviderName;
}

