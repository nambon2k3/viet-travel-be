package com.fpt.capstone.tourism.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String username;
    private String token;
    private String expirationTime;
}
