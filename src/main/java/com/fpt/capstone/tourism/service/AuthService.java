package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.TokenDTO;
import com.fpt.capstone.tourism.dto.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;

public interface AuthService {
    GeneralResponse<TokenDTO> login(UserDTO userDTO);
    GeneralResponse<UserInfoResponseDTO> register(RegisterRequestDTO registerRequestDTO) throws BusinessException;;
}
