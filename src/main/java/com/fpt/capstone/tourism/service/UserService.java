package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.UserDTO;
import com.fpt.capstone.tourism.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    String generateToken(User user);

    User findUserById(String id);

    User findUserByUsername(String username);




}
