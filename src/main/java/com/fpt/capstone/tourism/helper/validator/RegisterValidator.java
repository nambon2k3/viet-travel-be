package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import org.springframework.stereotype.Service;

@Service
public class RegisterValidator {



    public static boolean isRegisterValid(String username, String password, String rePassword, String fullName, String phone, String address, String email) {
        return Validator.isFieldValid(username, Validator::isUsernameValid, Constants.UserExceptionInformation.USERNAME_INVALID) &&
                Validator.isFieldValid(password, Validator::isPasswordValid, Constants.UserExceptionInformation.PASSWORD_INVALID) &&
                Validator.isFieldValid(rePassword, Validator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(fullName, Validator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                Validator.isFieldValid(phone, Validator::isPhoneValid, Constants.UserExceptionInformation.PHONE_INVALID) &&
                Validator.isFieldValid(address, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(email, Validator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);


    }


}
