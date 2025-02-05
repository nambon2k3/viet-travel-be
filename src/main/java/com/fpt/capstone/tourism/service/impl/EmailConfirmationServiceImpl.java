package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.EmailConfirmationService;
import com.fpt.capstone.tourism.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailConfirmationServiceImpl implements EmailConfirmationService {
    private final EmailService emailService;

    @Override
    public void sendConfirmationEmail(User user, String token) {
        try {

        String link = "http://localhost:8080/api/auth/confirm-email?token=" + generateTemporaryToken();
        String subject = "Viet Travel Email Confirmation";

            String content = "Dear " + user.getFullName() + ",\n\n"
                    + "Welcome to Viet Travel! We are thrilled to have you join our community."
                    + "\nWe hope you have fun and enjoy exploring Viet Nam with us.\n\n"
                    + "To confirm your email address, please click the link below:\n" + link;

            emailService.sendEmail(user.getEmail(), subject, content);
        } catch (Exception e) {
            throw BusinessException.of(Constants.Message.TOKEN_ENCRYPTION_FAILED_MESSAGE, e);
        }
    }
    public String generateTemporaryToken() {
        return UUID.randomUUID().toString();
    }


    @Override
    public void sendForgotPasswordEmail(User user, String token) {
        try {


            String link = "http://localhost:8080/api/reset-password?token=" + token;
            String subject = "Reset Password";
            String content = "Dear " + user.getFullName() + ",\n\n"
                    + "Hello,"
                    + "\nYou have requested to reset your password.\n\n"
                    + "Click the link below to change your password:\n" + link
                    +"\nIgnore this email if you do remember your password, or you have not made the request.";

            emailService.sendEmail(user.getEmail(), subject, content);
        } catch (Exception e) {
            throw BusinessException.of(Constants.Message.TOKEN_ENCRYPTION_FAILED_MESSAGE, e);
        }
    }


}
