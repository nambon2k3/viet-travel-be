package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;

public interface ForgotPasswordService {
    GeneralResponse<String> forgotPassword(String email);
    GeneralResponse<String> resetPassword(String token, String email, String newPassword, String newRePassword);
}
