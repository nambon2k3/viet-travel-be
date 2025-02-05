package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.RegisterConfirmRequestDTO;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInforResponseDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInitResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;

public interface AuthService {
    GeneralResponse<TokenDTO> login(UserDTO userDTO);
    GeneralResponse<RegisterInitResponseDTO> registerInit(RegisterRequestDTO registerRequestDTO);
    GeneralResponse<RegisterInforResponseDTO> registerConfirm(RegisterConfirmRequestDTO requestDTO);
}
