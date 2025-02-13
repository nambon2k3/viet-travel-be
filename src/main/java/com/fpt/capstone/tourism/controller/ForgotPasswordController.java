package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.ForgotPasswordRequestDTO;
import com.fpt.capstone.tourism.dto.request.PasswordResetDTO;
import com.fpt.capstone.tourism.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public GeneralResponse<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO email){
        return forgotPasswordService.forgotPassword(email.getEmail().trim().toLowerCase());
    }

    @PutMapping("/reset-password")
    public GeneralResponse<String> resetPassword(@RequestParam String token,
                                @RequestParam String email,
                                @RequestBody PasswordResetDTO passwordResetDTO){
        return forgotPasswordService.resetPassword(token, email, passwordResetDTO.getPassword(), passwordResetDTO.getRePassword());

    }
}
