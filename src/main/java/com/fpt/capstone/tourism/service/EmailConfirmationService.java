package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.model.EmailConfirmationToken;
import com.fpt.capstone.tourism.model.User;

public interface EmailConfirmationService {
    EmailConfirmationToken createEmailConfirmationToken(User user);
    void sendConfirmationEmail(User user, EmailConfirmationToken token) throws Exception;
    EmailConfirmationToken validateConfirmationToken(String token);
    void sendForgotPasswordEmail(User user, String token);
}
