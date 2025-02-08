package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserProfileResponseDTO;
import com.fpt.capstone.tourism.model.User;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    String generateToken(User user);
    User findUserById(String id);
    User findUserByUsername(String username);
    User saveUser(User userDTO);
    Boolean existsByUsername(String userName);
    Boolean exitsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);
    GeneralResponse<UserProfileResponseDTO> getUserProfile(String token);
    GeneralResponse<UserProfileResponseDTO> updateUserProfile(String token, Integer userId, UserProfileRequestDTO user);
    String getCurrentUser();

    GeneralResponse<String> changePassword(String token, String currentPassword, String newPassword, String newRePassword);

    GeneralResponse<String> updateAvatar(Integer userId, MultipartFile file);
}
