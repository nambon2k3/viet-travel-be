package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.model.User;

public interface EmailConfirmationService {

    void sendForgotPasswordEmail(User user, String token);

    void sendConfirmationEmail(User user, String token) throws Exception;

    String generateTemporaryToken();

}
