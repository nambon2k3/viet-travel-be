package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.function.Predicate;

import static com.fpt.capstone.tourism.constants.Constants.Regex.*;

public class CommonValidator {
    public static boolean isNullOrEmpty(String value){
        if(!(StringUtils.hasText(value))){
            throw BusinessException.of(HttpStatus.BAD_REQUEST,Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);
        }
        return true;
    }

    public static boolean isUsernameValid(String value) {
        return value.matches(REGEX_USERNAME);
    }

    public static boolean isPasswordValid(String value){
        return value.matches(REGEX_PASSWORD);
    }

    public static boolean isFullNameValid(String value){return value.trim().matches(REGEX_FULLNAME);}

    public static boolean isEmailValid(String value){
        return value.matches(REGEX_EMAIL);
    }
    public static boolean isPhoneValid(String value) { return value.matches(REGEX_PHONE);}

    public static boolean isFieldValid(String value, Predicate<String> validation, String message) {
        if (CommonValidator.isNullOrEmpty(value)) {
            if (validation != null && !validation.test(value)) {
                throw BusinessException.of(HttpStatus.BAD_REQUEST,message);
            }
        }
        return true;
    }


}
