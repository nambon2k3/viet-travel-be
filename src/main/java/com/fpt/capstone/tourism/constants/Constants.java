package com.fpt.capstone.tourism.constants;

public class Constants {


    public static final class UserExceptionInformation {

        public static final String USER_NOT_FOUND_CODE = "000100";
        public static final String USER_NOT_FOUND_MESSAGE = "User not found";
        public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Username already exists";
        public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Email already exists";
        public static final String FAIL_TO_SAVE_USER_MESSAGE = "Fail to save user";
        public static final String USER_INFORMATION_NULL_OR_EMPTY = "User information is null or empty";
        public static final String USERNAME_INVALID = "Username is invalid";
        public static final String PASSWORD_INVALID = "Password is invalid";
        public static final String FULL_NAME_INVALID = "FullName is invalid";
        public static final String EMAIL_INVALID = "Email is invalid";

    }


    public static final class Message {
        public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
        public static final String LOGIN_FAIL_MESSAGE = "Login Failed!";
        public static final String REGISTER_SUCCESS_MESSAGE = "User registered successfully";
        public static final String EMAIL_CONFIRMATION_REQUEST_MESSAGE = "Thank you for your registration, please check your email to complete verification";
        public static final String REGISTER_FAIL_MESSAGE = "User registered failed due to server error!";
        public static final String LOGOUT_SUCCESS_MESSAGE = "Logged out successfully";
        public static final String LOGOUT_FAIL_MESSAGE = "Logged out failed!";
        public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords do not match";
        public static final String EMAIL_NOT_CONFIRMED_MESSAGE = "Email not confirmed. Please check your email for the confirmation link.";
        public static final String INVALID_CONFIRMATION_TOKEN_MESSAGE = "Invalid or expired confirmation link.";
        public static final String TOKEN_USED_MESSAGE = "Email had already been confirmed before. Do not need to confirm again";
        public static final String EMAIL_CONFIRMED_SUCCESS_MESSAGE = "Registration successfully! Please log in to continue.";
        public static final String TOKEN_ENCRYPTION_FAILED_MESSAGE = "Encrypted token has failed.";
    }


    public static final class Regex {
        //public static final String REGEX_PASSWORD = "$d{8}^";
        public static final String REGEX_USERNAME= "^[a-zA-Z0-9-_]{8,30}$";
        public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        public static final String REGEX_FULLNAME = "^[a-zA-Z\s]*$";
        public static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    }


}
