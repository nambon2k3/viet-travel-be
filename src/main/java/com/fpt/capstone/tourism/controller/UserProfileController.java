package com.fpt.capstone.tourism.controller;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> getUserProfile(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserProfile(token));
    }
}
