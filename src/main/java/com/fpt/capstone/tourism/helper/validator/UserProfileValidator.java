package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;

public class UserProfileValidator {
    public static boolean isProfileValid(String fullName, String email, String phone, String address) {
        return  CommonValidator.isFieldValid(address, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                CommonValidator.isFieldValid(phone, CommonValidator::isPhoneValid, Constants.UserExceptionInformation.PHONE_INVALID) &&
                CommonValidator.isFieldValid(fullName, CommonValidator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                CommonValidator.isFieldValid(email, CommonValidator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);

    }
}
