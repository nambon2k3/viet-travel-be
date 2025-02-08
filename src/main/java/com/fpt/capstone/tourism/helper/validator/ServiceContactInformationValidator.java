package com.fpt.capstone.tourism.helper.validator;

import com.fpt.capstone.tourism.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

public class ServiceContactInformationValidator {
    public static void validateServiceContact(String fullName, String phoneNumber, String email, String position) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, EMPTY_FULL_NAME);
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, EMPTY_PHONE_NUMBER);
        }
        if (!phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, INVALID_PHONE_NUMBER);
        }
        if (email == null || email.trim().isEmpty()) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, EMPTY_EMAIL);
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, INVALID_EMAIL);
        }
        if (position == null || position.trim().isEmpty()) {
            throw BusinessException.of(HttpStatus.BAD_REQUEST, EMPTY_POSITION);
        }
    }
}
