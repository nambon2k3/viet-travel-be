package com.fpt.capstone.tourism.controller;

import com.cloudinary.utils.ObjectUtils;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.PasswordChangeDTO;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserProfileResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.CloudinaryService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @PostMapping()
    public ResponseEntity<GeneralResponse<UserProfileResponseDTO>> getUserProfile(
            @RequestHeader("Authorization") String token
    ){
        return ResponseEntity.ok(userService.getUserProfile(token));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<GeneralResponse<UserProfileResponseDTO>> updateUserProfile(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId,
            @RequestBody UserProfileRequestDTO userProfileRequestDTO
            ){
        return ResponseEntity.ok(userService.updateUserProfile(token, userId, userProfileRequestDTO));
    }

    @PostMapping("/change-password")
    public ResponseEntity<GeneralResponse<String>> changePassword(@RequestHeader("Authorization") String token,
                                 @RequestBody PasswordChangeDTO passwordChangeDTO){
        return ResponseEntity.ok(userService.changePassword(token, passwordChangeDTO.getCurrentPassword(),
                passwordChangeDTO.getNewPassword(), passwordChangeDTO.getNewRePassword()));
    }
    @PostMapping(value = "/avatar/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId,
                                          @RequestParam("avatar")MultipartFile file
                                          ){
        return ResponseEntity.ok(userService.updateAvatar(userId, file));
    }
}
