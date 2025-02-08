package com.fpt.capstone.tourism.helper.validator;
import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import java.util.function.Predicate;
public class Validator {
    public static boolean isProfileValid(String fullName, String email, String phone, String address) {
        return  Validator.isFieldValid(address, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(phone, Validator::isPhoneValid, Constants.UserExceptionInformation.PHONE_INVALID) &&
                Validator.isFieldValid(fullName, Validator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                Validator.isFieldValid(email, Validator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);

    }
    public static boolean isRegisterValid(String username, String password, String rePassword, String fullName, String phone, String address, String email) {
        return Validator.isFieldValid(username, Validator::isUsernameValid, Constants.UserExceptionInformation.USERNAME_INVALID) &&
                Validator.isFieldValid(password, Validator::isPasswordValid, Constants.UserExceptionInformation.PASSWORD_INVALID) &&
                Validator.isFieldValid(rePassword, Validator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(fullName, Validator::isFullNameValid, Constants.UserExceptionInformation.FULL_NAME_INVALID) &&
                Validator.isFieldValid(phone, Validator::isPhoneValid, Constants.UserExceptionInformation.PHONE_INVALID) &&
                Validator.isFieldValid(address, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(email, Validator::isEmailValid, Constants.UserExceptionInformation.EMAIL_INVALID);

    }

    public static boolean isLoginValid(String username, String password) {
        return Validator.isFieldValid(username, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY) &&
                Validator.isFieldValid(password, null, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);

    }
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
        return value.trim().matches(Constants.Regex.REGEX_FULLNAME);
    }

    public static boolean isEmailValid(String value){
        return value.matches(Constants.Regex.REGEX_EMAIL);
    }
    public static boolean isPhoneValid(String value) { return value.matches(Constants.Regex.REGEX_PHONE);}

    public static boolean isFieldValid(String value, Predicate<String> validation, String message) {
        if (Validator.isNullOrEmpty(value)) {
            return false;
        }
        if (validation != null && !validation.test(value)) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, message);
        }
        return true;
    }
}
