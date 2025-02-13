package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeoPositionDTO;
import com.fpt.capstone.tourism.dto.common.LocationDTO;
import com.fpt.capstone.tourism.dto.common.ServiceCategoryDTO;
import com.fpt.capstone.tourism.dto.common.ServiceProviderDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.Regex.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

public class Validator {

    // General Validation
    // General Validation
    public static void validateRegex(String value, String regex, String errorMessage) {
        if (!value.matches(regex)) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    public static void isNullOrEmpty(String value, String errorMessage) {
        if (!StringUtils.hasText(value)) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }
    public static boolean isPasswordValid(String value){
        return value.matches(REGEX_PASSWORD);
    }

    // Authentication Validation
    public static void validateLogin(String username, String password) {
        isNullOrEmpty(username, EMPTY_USERNAME);
        validateRegex(username, REGEX_USERNAME, USERNAME_INVALID);
        isNullOrEmpty(password, EMPTY_PASSWORD);
        validateRegex(password, REGEX_PASSWORD, PASSWORD_INVALID);
    }

    public static void validateRegister(String username, String password, String rePassword, String fullName,
                                        String phone, String address, String email) {
        isNullOrEmpty(username, EMPTY_USERNAME);
        validateRegex(username, REGEX_USERNAME, USERNAME_INVALID);

        isNullOrEmpty(password, EMPTY_PASSWORD);
        validateRegex(password, REGEX_PASSWORD, PASSWORD_INVALID);

        isNullOrEmpty(rePassword, EMPTY_REPASSWORD);
        if (!password.equals(rePassword)) {
            throw BusinessException.of(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }

        isNullOrEmpty(fullName, EMPTY_FULL_NAME);
        validateRegex(fullName, REGEX_FULLNAME, FULL_NAME_INVALID);

        isNullOrEmpty(phone, EMPTY_PHONE_NUMBER);
        validateRegex(phone, REGEX_PHONE, PHONE_INVALID);

        isNullOrEmpty(address, EMPTY_ADDRESS);

        isNullOrEmpty(email, EMPTY_EMAIL);
        validateRegex(email, REGEX_EMAIL, EMAIL_INVALID);
    }

    // Common Feature Validation
    public static void validateProfile(String fullName, String email, String phone, String address) {
        isNullOrEmpty(fullName, EMPTY_FULL_NAME);
        validateRegex(fullName, REGEX_FULLNAME, FULL_NAME_INVALID);

        isNullOrEmpty(email, EMPTY_EMAIL);
        validateRegex(email, REGEX_EMAIL, EMAIL_INVALID);

        isNullOrEmpty(phone, EMPTY_PHONE_NUMBER);
        validateRegex(phone, REGEX_PHONE, PHONE_INVALID);

        isNullOrEmpty(address, USER_INFORMATION_NULL_OR_EMPTY);
    }

    // User Management Validation
    public static void validateUserCreation(String fullName, String username, String password, String rePassword,
                                            String email, String gender, String phone, String address,
                                            String avatarImage, List<String> roleNames) {
        validateUserFields(fullName, username, password, rePassword, email, gender, phone, address, avatarImage, roleNames);
    }

    public static void validateUserUpdate(String fullName, String username, String password, String rePassword,
                                          String email, String gender, String phone, String address,
                                          String avatarImage, List<String> roleNames) {
        validateUserFields(fullName, username, password, rePassword, email, gender, phone, address, avatarImage, roleNames);

        // Only validate password if provided
        if (StringUtils.hasText(password)) {
            validateRegex(password, REGEX_PASSWORD, PASSWORD_INVALID);
        }
    }

    private static void validateUserFields(String fullName, String username, String password, String rePassword,
                                           String email, String gender, String phone, String address,
                                           String avatarImage, List<String> roleNames) {
        isNullOrEmpty(fullName, EMPTY_FULL_NAME);
        validateRegex(fullName, REGEX_FULLNAME, FULL_NAME_INVALID);

        isNullOrEmpty(username, EMPTY_USERNAME);
        validateRegex(username, REGEX_USERNAME, USERNAME_INVALID);

        isNullOrEmpty(password, EMPTY_PASSWORD);
        validateRegex(password, REGEX_PASSWORD, PASSWORD_INVALID);

        isNullOrEmpty(rePassword, EMPTY_REPASSWORD);
        if (!password.equals(rePassword)) {
            throw BusinessException.of(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }
        isNullOrEmpty(email, EMPTY_EMAIL);
        validateRegex(email, REGEX_EMAIL, EMAIL_INVALID);

        isNullOrEmpty(phone, EMPTY_PHONE_NUMBER);
        validateRegex(phone, REGEX_PHONE, PHONE_INVALID);

        isNullOrEmpty(address, EMPTY_ADDRESS);
        //isNullOrEmpty(avatarImage, USER_INFORMATION_NULL_OR_EMPTY);

        if (!"male".equalsIgnoreCase(gender) && !"female".equalsIgnoreCase(gender)) {
            throw BusinessException.of(GENDER_INVALID);
        }

        if (roleNames == null || roleNames.isEmpty()) {
            throw BusinessException.of(ROLES_NAME_INVALID);
        }
    }

    // Service Contact Validation
    public static void validateServiceContact(String fullName, String phoneNumber, String email, String position) {
        isNullOrEmpty(fullName, EMPTY_FULL_NAME);
        isNullOrEmpty(phoneNumber, EMPTY_PHONE_NUMBER);
        validateRegex(phoneNumber, REGEX_PHONE, PHONE_INVALID);
        isNullOrEmpty(email, EMPTY_EMAIL);
        validateRegex(email, REGEX_EMAIL, EMAIL_INVALID);
        isNullOrEmpty(position, EMPTY_POSITION);
    }


    public static void validateServiceProvider(ServiceProviderDTO serviceProviderDTO) {
        isNullOrEmpty(serviceProviderDTO.getImageUrl(), EMPTY_IMAGE_URL);

        isNullOrEmpty(serviceProviderDTO.getAbbreviation(), EMPTY_ABBREVIATION);

        isNullOrEmpty(serviceProviderDTO.getWebsite(), EMPTY_WEBSITE);

        isNullOrEmpty(serviceProviderDTO.getEmail(), EMPTY_EMAIL);
        validateRegex(serviceProviderDTO.getEmail(), REGEX_EMAIL, INVALID_EMAIL);

        isNullOrEmpty(serviceProviderDTO.getPhone(), EMPTY_PHONE_NUMBER);
        validateRegex(serviceProviderDTO.getPhone(), REGEX_PHONE, INVALID_PHONE_NUMBER);

        isNullOrEmpty(serviceProviderDTO.getAddress(), EMPTY_ADDRESS);
//        private LocationDTO location;
//
//        private GeoPositionDTO geoPosition;
//
//        private Set<ServiceCategoryDTO> serviceCategorys;

    }

}
