package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.EmailConfirmationToken;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.EmailConfirmationService;
import com.fpt.capstone.tourism.service.ForgotPasswordService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final UserService userService;
    private final EmailConfirmationService emailConfirmationService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String forgotPassword(String email) {

        User user = userService.findUserByEmail(email);

        //Check email valid or not
        if(!userService.exitsByEmail(email)){
            return Constants.UserExceptionInformation.EMAIL_INVALID;
        }

        //Generate token
        String token = generateToken(email);

        //Send email to reset password
        emailConfirmationService.sendForgotPasswordEmail(user, token);
        return token;
    }

    @Override
    public String resetPassword(String token, String email, String newPassword, String newRePassword) {

            //Check token expired
            byte[] decodedByte = Base64.getDecoder().decode(token);
            String decodedString = new String(decodedByte, StandardCharsets.UTF_8);
            String[] parts = decodedString.split(":");

            String tokenEmail = parts[0];
            Long createdTime = Long.parseLong(parts[1]);
            Long currentTime = System.currentTimeMillis();

            if(email.equals(tokenEmail)){
                throw BusinessException.of(HttpStatus.FORBIDDEN.toString());
            }

            if(currentTime > createdTime + 10 * 60 * 1000){
                throw BusinessException.of(Constants.Message.TOKEN_EXPIRED_MESSAGE);
            }

            //Check valid password
            if(!(Validator.isPasswordValid(newPassword) || Validator.isPasswordValid(newRePassword))){
                throw BusinessException.of(Constants.UserExceptionInformation.PASSWORD_INVALID);
            }

            if(!newPassword.equals(newRePassword)){
                throw BusinessException.of(Constants.Message.PASSWORDS_DO_NOT_MATCH_MESSAGE);
            }

            User user = userService.findUserByEmail(email);
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);


        return Constants.Message.PASSWORD_UPDATED_SUCCESS_MESSAGE;
    }

    public String generateToken(String email){
        Long currentTimes = System.currentTimeMillis();
        String tokenRaw = email + ":" + currentTimes;
        return Base64.getEncoder().encodeToString(tokenRaw.getBytes(StandardCharsets.UTF_8));
    }


}
