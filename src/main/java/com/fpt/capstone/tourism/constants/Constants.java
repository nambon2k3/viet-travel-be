package com.fpt.capstone.tourism.constants;

public class Constants {


    public static final class UserExceptionInformation {

        public static final String USER_NOT_FOUND_CODE = "000100";
        public static final String USER_NOT_FOUND_MESSAGE = "User not found";
        public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Username already exists";
        public static final String FAIL_TO_SAVE_USER_MESSAGE = "Fail to save user";

    }


    public static final class Message {
        public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
        public static final String LOGIN_FAIL_MESSAGE = "Login Failed!";
        public static final String REGISTER_SUCCESS_MESSAGE = "User registered successfully";
        public static final String REGISTER_FAIL_MESSAGE = "User registered failed!";
        public static final String LOGOUT_SUCCESS_MESSAGE = "Logged out successfully";
        public static final String LOGOUT_FAIL_MESSAGE = "Logged out failed!";
    }


    public static final class Regex {
        public static final String REGEX_PASSWORD = "$d{8}^";

    }


}
