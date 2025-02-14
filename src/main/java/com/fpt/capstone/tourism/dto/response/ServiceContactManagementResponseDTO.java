package com.fpt.capstone.tourism.dto.response;


import com.fpt.capstone.tourism.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceContactManagementResponseDTO {
    private Long id;
    private String position;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String serviceProviderName;
}
