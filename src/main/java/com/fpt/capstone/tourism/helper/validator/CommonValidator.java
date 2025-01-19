package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;


public class CommonValidator {
    public static boolean isNullOrEmpty(String value){
        if(!(StringUtils.hasText(value))){
            throw BusinessException.of(Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);
        }
        return true;
    }
    public static boolean isUsernameValid(String value) {
        return value.matches(Constants.Regex.REGEX_USERNAME);
    }

    public static boolean isPasswordValid(String value){
        return value.matches(Constants.Regex.REGEX_PASSWORD);
    }

    public static boolean isFullNameValid(String value){
        return value.matches(Constants.Regex.REGEX_FULLNAME);
    }

    public static boolean isEmailValid(String value){
        return value.matches(Constants.Regex.REGEX_EMAIL);
    }

    public static boolean isFieldValid(String value, Predicate<String> validation, String message) {
        if (CommonValidator.isNullOrEmpty(value)) {
            if (!validation.test(value)) {
                throw BusinessException.of(message);
            }
        }
        return true;
    }


}
