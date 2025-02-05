package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class RegisterValidator {
    public static boolean isRegisterValid(String username, String password, String rePassword, String fullName, String phone, String address, String email) {
        return CommonValidator.isFieldValid(username, CommonValidator::isUsernameValid, Constants.UserExceptionInformation.USERNAME_INVALID) &&
                CommonValidator.isFieldValid(password, CommonValidator::isPasswordValid, Constants.UserExceptionInformation.PASSWORD_INVALID) &&
                CommonValidator.isFieldValid(rePassword, CommonValidator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                CommonValidator.isFieldValid(fullName, CommonValidator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                CommonValidator.isFieldValid(phone, CommonValidator::isPhoneValid, Constants.UserExceptionInformation.PHONE_INVALID) &&
                CommonValidator.isFieldValid(address, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                CommonValidator.isFieldValid(email, CommonValidator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);

    }


}
