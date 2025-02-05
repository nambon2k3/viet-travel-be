package com.fpt.capstone.tourism.controller;


import com.fpt.capstone.tourism.dto.request.PasswordResetDTO;
import com.fpt.capstone.tourism.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody String email){
        return forgotPasswordService.forgotPassword(email);
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String email,
                                @RequestBody PasswordResetDTO passwordResetDTO){
        return forgotPasswordService.resetPassword(token, email, passwordResetDTO.getPassword(), passwordResetDTO.getRePassword());

    }
}
