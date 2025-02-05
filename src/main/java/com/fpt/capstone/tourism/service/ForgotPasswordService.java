package com.fpt.capstone.tourism.service;

public interface ForgotPasswordService {
    String forgotPassword(String email);
    String resetPassword(String token, String email, String newPassword, String newRePassword);
}
