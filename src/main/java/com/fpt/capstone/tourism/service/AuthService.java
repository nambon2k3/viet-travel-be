package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;

public interface AuthService {
    GeneralResponse<TokenDTO> login(UserDTO userDTO);
}
