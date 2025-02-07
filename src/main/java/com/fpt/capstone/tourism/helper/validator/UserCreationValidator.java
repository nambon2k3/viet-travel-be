package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;
import static com.fpt.capstone.tourism.constants.Constants.Message.*;
@Service
public class UserCreationValidator {

    public static void validateUserCreation(String fullName, String username, String password, String rePassword,
                                            String email, String gender, String phone, String address, String avatarImage, List<String> roleNames) {
        // Ensure fields are not null or empty
        CommonValidator.isNullOrEmpty(fullName);
        CommonValidator.isNullOrEmpty(username);
        CommonValidator.isNullOrEmpty(password);
        CommonValidator.isNullOrEmpty(rePassword);
        CommonValidator.isNullOrEmpty(email);
        CommonValidator.isNullOrEmpty(gender);
        CommonValidator.isNullOrEmpty(phone);
        CommonValidator.isNullOrEmpty(address);
        CommonValidator.isNullOrEmpty(avatarImage);
        CommonValidator.isNullOrEmpty(roleNames.toString());

        // Validate fields based on specific rules
        CommonValidator.isFieldValid(username, CommonValidator::isUsernameValid, USERNAME_INVALID);
        CommonValidator.isFieldValid(password, CommonValidator::isPasswordValid, PASSWORD_INVALID);
        CommonValidator.isFieldValid(rePassword, CommonValidator::isPasswordValid, PASSWORD_INVALID);
        CommonValidator.isFieldValid(fullName, CommonValidator::isFullNameValid, FULL_NAME_INVALID);
        CommonValidator.isFieldValid(email, CommonValidator::isEmailValid, EMAIL_INVALID);
        CommonValidator.isFieldValid(phone, CommonValidator::isPhoneValid, PHONE_INVALID);

        if (!password.equals(rePassword)) {
            throw BusinessException.of(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }

        if (!"male".equalsIgnoreCase(gender) && !"female".equalsIgnoreCase(gender)) {
            throw BusinessException.of(GENDER_INVALID);
        }

        if (roleNames.isEmpty()) {
            throw BusinessException.of(ROLES_NAME_INVALID);
        }
    }

    public static void validateUserUpdate(String fullName, String username, String password, String rePassword,
                                          String email, String gender, String phone, String address, String avatarImage, List<String> roleNames) {
        // Ensure fields are not null or empty
        CommonValidator.isNullOrEmpty(fullName);
        CommonValidator.isNullOrEmpty(username);
        CommonValidator.isNullOrEmpty(password);
        CommonValidator.isNullOrEmpty(rePassword);
        CommonValidator.isNullOrEmpty(email);
        CommonValidator.isNullOrEmpty(gender);
        CommonValidator.isNullOrEmpty(phone);
        CommonValidator.isNullOrEmpty(address);
        CommonValidator.isNullOrEmpty(avatarImage);
        CommonValidator.isNullOrEmpty(roleNames.toString());

        // Validate fields based on specific rules
        CommonValidator.isFieldValid(username, CommonValidator::isUsernameValid, USERNAME_INVALID);
        CommonValidator.isFieldValid(password, CommonValidator::isPasswordValid, PASSWORD_INVALID);
        CommonValidator.isFieldValid(rePassword, CommonValidator::isPasswordValid, PASSWORD_INVALID);
        CommonValidator.isFieldValid(fullName, CommonValidator::isFullNameValid, FULL_NAME_INVALID);
        CommonValidator.isFieldValid(email, CommonValidator::isEmailValid, EMAIL_INVALID);
        CommonValidator.isFieldValid(phone, CommonValidator::isPhoneValid, PHONE_INVALID);

        if (!password.equals(rePassword)) {
            throw BusinessException.of(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }

        // Validate password only if provided
        if (password != null && !password.isEmpty()) {
            CommonValidator.isFieldValid(password, CommonValidator::isPasswordValid, PASSWORD_INVALID);
        }
    }
}
