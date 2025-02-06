package com.fpt.capstone.tourism.constants;

public class Constants {


    public static final class UserExceptionInformation {

        public static final String USER_NOT_FOUND_CODE = "000100";
        public static final String USER_NOT_FOUND_MESSAGE = "User not found";
        public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Username already exists";
        public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Email already exists";
        public static final String PHONE_ALREADY_EXISTS_MESSAGE = "Phone already exists";
        public static final String FAIL_TO_SAVE_USER_MESSAGE = "Fail to save user";
        public static final String USER_INFORMATION_NULL_OR_EMPTY = "This section is required";
        public static final String USERNAME_INVALID = "Username contains only letters, numbers, -, _ with range from 8 to 30";
        public static final String PASSWORD_INVALID = "Password contains 8 characters or more (must contain 1 uppercase letter, " +
                "1 lowercase letter and 1 special character";
        public static final String FULL_NAME_INVALID = "Full name starts with a letter, use only letters and white space";
        public static final String PHONE_INVALID = "Phone must contain 10 characters";
        public static final String EMAIL_INVALID = "Email is invalid";
        public static final String USER_NOT_FOUND = "User not found, please login with a valid account to see your profile";
    }


    public static final class Message {
        public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
        public static final String LOGIN_FAIL_MESSAGE = "Login Failed! Invalid username or password";
        public static final String EMAIL_CONFIRMATION_REQUEST_MESSAGE = "Thank you for your registration, please check your email to complete verification";
        public static final String REGISTER_FAIL_MESSAGE = "User registered failed due to server error!";
        public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords do not match";
        public static final String PASSWORDS_INCORRECT_MESSAGE = "Current password is incorrect";
        public static final String CHANGE_PASSWORD_SUCCESS_MESSAGE = "Change password successfully";
        public static final String CHANGE_PASSWORD_FAIL_MESSAGE = "Change password fail";
        public static final String EMAIL_NOT_CONFIRMED_MESSAGE = "Email not confirmed. Please check your email for the confirmation link.";
        public static final String INVALID_CONFIRMATION_TOKEN_MESSAGE = "Invalid or expired confirmation link.";
        public static final String TOKEN_USED_MESSAGE = "Email had already been confirmed before. Do not need to confirm again";
        public static final String EMAIL_CONFIRMED_SUCCESS_MESSAGE = "Registration successfully! Please log in to continue.";
        public static final String TOKEN_ENCRYPTION_FAILED_MESSAGE = "Encrypted token has failed.";

        public static final String USER_NOT_AUTHENTICATED = "User is not authenticated";
        public static final String PROFILE_UPDATE_SUCCESS = "Successfully update user profile";
        public static final String PROFILE_UPDATE_FAIL = "Update user profile fail";
        public static final String GET_PROFILE_SUCCESS = "Successfully get user profile";
        public static final String GET_PROFILE_FAIL = "Get user profile fail";

        public static final String INVALID_REGISTER_INFO= "Register information is invalid";
        public static final String INVALID_CONFIRMATION_TOKEN = "Invalid confirmation token, please check again";

    }


    public static final class Regex {
        //public static final String REGEX_PASSWORD = "$d{8}^";
        public static final String REGEX_USERNAME= "^[a-zA-Z0-9-_]{8,30}$";
        public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        public static final String REGEX_FULLNAME = "^[a-zA-Z][a-zA-Z\s]*$";
        public static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        public static final String REGEX_PHONE = "^[0-9]{10}$";

    }


}
