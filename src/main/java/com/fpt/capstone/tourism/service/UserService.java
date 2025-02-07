package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.UserManageGeneralInformationResponseDTO;
import com.fpt.capstone.tourism.model.User;

import java.util.List;


public interface UserService {
    String generateToken(User user);
    User findUserById(String id);
    User findUserByUsername(String username);
    User saveUser(User userDTO);
    Boolean existsByUsername(String userName);
    Boolean exitsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);
    //CRUD User
    GeneralResponse<?> getUserById(Long id);
    GeneralResponse<?> createUser(UserCreationRequestDTO userDTO);
    GeneralResponse<?> updateUser(Long id, UserCreationRequestDTO userDTO);
    GeneralResponse<?> deleteUser(Long id);

    GeneralResponse<PagingDTO<List<UserManageGeneralInformationResponseDTO>>> getAllUser(int page, int size);
    void createEmailConfirmationToken(User user, String token);
    User findUserByEmailConfirmationToken(String token);
    void deleteEmailConfirmationToken(String token);
}
