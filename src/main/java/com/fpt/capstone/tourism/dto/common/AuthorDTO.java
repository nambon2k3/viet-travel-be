package com.fpt.capstone.tourism.dto.common;

import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;
    private String fullName;
    private String avatarImage;
    private String email;
}
