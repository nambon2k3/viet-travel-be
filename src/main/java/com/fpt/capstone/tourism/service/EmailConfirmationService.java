package com.fpt.capstone.tourism.service;

import com.fpt.capstone.tourism.model.Token;
import com.fpt.capstone.tourism.model.User;

public interface EmailConfirmationService {
    Token createEmailConfirmationToken(User user);
    void sendConfirmationEmail(User user, Token token) throws Exception;
    Token validateConfirmationToken(String token);
}