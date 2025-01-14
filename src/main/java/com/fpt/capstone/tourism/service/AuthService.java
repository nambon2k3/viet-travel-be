package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.TokenDTO;
import com.fpt.capstone.tourism.dto.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;

public interface AuthService {
    GeneralResponse<TokenDTO> login(UserDTO userDTO);
}
