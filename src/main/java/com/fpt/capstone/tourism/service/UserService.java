package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.model.User;


public interface UserService {
    String generateToken(User user);
    User findUserById(String id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User saveUser(User userDTO);
    Boolean existsByUsername(String userName);
    Boolean exitsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);
}
