package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.RegisterConfirmRequestDTO;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInforResponseDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInitResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<GeneralResponse<TokenDTO>> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.login(userDTO));
    }

    @PostMapping("/register-init")
    public ResponseEntity<GeneralResponse<RegisterInitResponseDTO>> registerInit(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authService.registerInit(registerRequestDTO));
    }

    @PostMapping("/register-confirm")
    public ResponseEntity<GeneralResponse<RegisterInforResponseDTO>> registerConfirm(@RequestBody RegisterConfirmRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.registerConfirm(requestDTO));
    }


}

