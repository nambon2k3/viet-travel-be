package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.model.User;

public interface EmailConfirmationService {
    void sendConfirmationEmail(User user, String token) throws Exception;

    String generateTemporaryToken();
}
