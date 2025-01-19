package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class RegisterValidator {
    public static boolean isRegisterValid(String username, String password, String fullName, String email) {
        return CommonValidator.isFieldValid(username, CommonValidator::isUsernameValid, Constants.UserExceptionInformation.USERNAME_INVALID) &&
                CommonValidator.isFieldValid(password, CommonValidator::isPasswordValid, Constants.UserExceptionInformation.PASSWORD_INVALID) &&
                CommonValidator.isFieldValid(fullName, CommonValidator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                CommonValidator.isFieldValid(email, CommonValidator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);

    }


}
