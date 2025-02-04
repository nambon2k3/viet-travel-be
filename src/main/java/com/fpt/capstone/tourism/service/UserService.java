package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.model.User;


public interface UserService {
    String generateToken(User user);
    User findUserById(String id);
    User findUserByUsername(String username);
    User saveUser(User userDTO);
    Boolean existsByUsername(String userName);
    Boolean exitsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);
    void createEmailConfirmationToken(User user, String token);
    User findUserByEmailConfirmationToken(String token);
    void deleteEmailConfirmationToken(String token);
    GeneralResponse<UserInfoResponseDTO> getUserProfile(String token);
    GeneralResponse<UserInfoResponseDTO> updateUserProfile(String token, Integer userId, UserProfileRequestDTO user);
    String getCurrentUser();
}
