package com.fpt.capstone.tourism.controller;

import com.cloudinary.utils.ObjectUtils;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.CloudinaryService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> getUserProfile(){
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userService.getUserProfile(currentUser));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> updateUserProfile(
            @PathVariable Integer userId,
            @RequestBody UserProfileRequestDTO userProfileRequestDTO
            ){
        User currentUser = userService.getCurrentUser();

        if(!userId.equals(currentUser.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.updateUserProfile(userId, userProfileRequestDTO));
    }

    @PostMapping("/avatar")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("avatar")MultipartFile[] file){
        return ResponseEntity.ok(cloudinaryService.uploadFile(file));
    }





}
