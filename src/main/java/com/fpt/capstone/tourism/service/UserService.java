package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.UserManageGeneralInformationDTO;
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
    GeneralResponse<?> getUserById(int id);
    GeneralResponse<?> createUser(UserCreationRequestDTO userDTO);
    GeneralResponse<?> updateUser(int id, UserCreationRequestDTO userDTO);
    GeneralResponse<?> deleteUser(int id);

    GeneralResponse<PagingDTO<List<UserManageGeneralInformationDTO>>> getAllUser(int page, int size);
}
