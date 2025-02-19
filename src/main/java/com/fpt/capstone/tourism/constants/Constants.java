package com.fpt.capstone.tourism.constants;


public class Constants {
    public static final class UserExceptionInformation {
        public static final String USER_NOT_FOUND_CODE = "000100";
        public static final String USER_NOT_FOUND_MESSAGE = "User not found";
        public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "Username already exists";
        public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Email already exists";
        public static final String PHONE_ALREADY_EXISTS_MESSAGE = "Phone already exists";
        public static final String FAIL_TO_SAVE_USER_MESSAGE = "Fail to save user";
        public static final String GENDER_INVALID = "Gender is invalid";
        public static final String USER_INFORMATION_NULL_OR_EMPTY = "This section is required";
        public static final String USERNAME_INVALID = "Username contains only letters, numbers, -, _ with range from 8 to 30";
        public static final String PASSWORD_INVALID = "Password contains 8 characters or more (must contain 1 uppercase letter, " +
                "1 lowercase letter and 1 special character";
        public static final String FULL_NAME_INVALID = "Full name starts with a letter, use only letters and white space";
        public static final String PHONE_INVALID = "Phone must contain 10 characters";
        public static final String EMAIL_INVALID = "Email is invalid";
        public static final String ROLES_NAME_INVALID = "Role name is invalid";
        public static final String USER_NOT_FOUND = "User not found, please login with a valid account to see your profile";
    }


    public static final class Message {
        public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
        public static final String PASSWORD_UPDATED_SUCCESS_MESSAGE = "Your password is updated successfully";
        public static final String PASSWORD_UPDATED_FAIL_MESSAGE = "Your password is updated fail";
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
        public static final String TOKEN_EXPIRED_MESSAGE = "Token expired.";
        public static final String USER_NOT_AUTHENTICATED = "User is not authenticated";
        public static final String UPDATE_PROFILE_SUCCESS = "Successfully update user profile";
        public static final String UPDATE_PROFILE_FAIL = "Update user profile fail";
        public static final String UPDATE_AVATAR_SUCCESS = "Update avatar successfully";
        public static final String UPDATE_AVATAR_FAIL = "Update avatar fail";
        public static final String GET_PROFILE_SUCCESS = "Successfully get user profile";
        public static final String GET_PROFILE_FAIL = "Get user profile fail";
        public static final String INVALID_REGISTER_INFO= "Register information is invalid";
        public static final String INVALID_CONFIRMATION_TOKEN = "Invalid confirmation token, please check again";
        public static final String CONFIRM_EMAIL_FAILED = "Confirm email failed";
        public static final String CREATE_BLOG_SUCCESS_MESSAGE = "Blog created successfully";
        public static final String CREATE_BLOG_FAIL_MESSAGE = "Blog created failed";
        public static final String GENERAL_SUCCESS_MESSAGE = "Successfully";
        public static final String GENERAL_FAIL_MESSAGE = "Failed";
        public static final String DUPLICATE_SERVICE_CONTACT_PHONE = "Phone number already exists.";
        public static final String SERVICE_CONTACT_NOT_FOUND = "Service contact not found.";
        public static final String CREATE_SERVICE_CONTACT_SUCCESS = "Service contact created successfully.";
        public static final String CREATE_SERVICE_CONTACT_FAIL = "Failed to create service contact.";
        public static final String GET_SERVICE_CONTACT_SUCCESS = "Service contact retrieved successfully.";
        public static final String GET_SERVICE_CONTACT_FAIL = "Failed to retrieve service contact.";
        public static final String GET_ALL_SERVICE_CONTACTS_SUCCESS = "Service contacts retrieved successfully.";
        public static final String GET_ALL_SERVICE_CONTACTS_FAIL = "Failed to retrieve service contacts.";
        public static final String SERVICE_CONTACTS_NOT_EXITS = "Failed to retrieve service contacts because service contact is not exits.";
        public static final String UPDATE_SERVICE_CONTACT_SUCCESS = "Service contact updated successfully.";
        public static final String UPDATE_SERVICE_CONTACT_FAIL = "Failed to update service contact.";
        public static final String DELETE_SERVICE_CONTACT_SUCCESS = "Service contact deleted successfully.";
        public static final String DELETE_SERVICE_CONTACT_FAIL = "Failed to delete service contact.";
        public static final String EMPTY_FULL_NAME = "Full name cannot be empty";
        public static final String EMPTY_USERNAME = "User name cannot be empty";
        public static final String EMPTY_PASSWORD = "Password cannot be empty";
        public static final String EMPTY_REPASSWORD = "Re Password cannot be empty";
        public static final String EMPTY_ADDRESS = "Address cannot be empty";
        public static final String EMPTY_PHONE_NUMBER = "Phone number cannot be empty";
        public static final String INVALID_PHONE_NUMBER = "Invalid phone number format. Must be 10-15 digits.";
        public static final String EMPTY_EMAIL = "Email cannot be empty";
        public static final String INVALID_EMAIL = "Invalid email format";
        public static final String EMPTY_POSITION = "Position cannot be empty";

        public static final String EMPTY_IMAGE_URL = "Image URL cannot be empty";
        public static final String EMPTY_ABBREVIATION = "Abbreviation cannot be empty";
        public static final String EMPTY_WEBSITE = "Website cannot be empty";
        public static final String DUPLICATE_SERVICE_CONTACT_EMAIL= "This contact email already exists.";
        public static final String SERVICE_PROVIDER_NOT_FOUND = "Service provider not found. Please try again.";
        public static final String RESET_PASSWORD_REQUEST_SUCCESS = "User request reset password successfully";
        public static final String RESET_PASSWORD_REQUEST_FAIL = "User request reset password fail";
        public static final String GET_USER_SUCCESS_MESSAGE = "Get user successfully";
        public static final String GET_USER_FAIL_MESSAGE = "Get user failed";
        public static final String DUPLICATE_USERNAME_MESSAGE = "Username already exists, please choose another one";
        public static final String ROLE_NOT_FOUND = "Role not found";
        public static final String CREATE_USER_SUCCESS_MESSAGE = "User created successfully";
        public static final String CREATE_USER_FAIL_MESSAGE = "User created failed";
        public static final String UPDATE_USER_SUCCESS_MESSAGE = "User updated successfully";
        public static final String UPDATE_USER_FAIL_MESSAGE = "User updated failed";
        public static final String DELETE_USER_SUCCESS_MESSAGE = "User deleted successfully";
        public static final String DELETE_USER_FAIL_MESSAGE = "User deleted failed";
        public static final String GET_ALL_USER_SUCCESS_MESSAGE = "Get all users successfully";
        public static final String GET_ALL_USER_FAIL_MESSAGE = "Get all users failed";
        public static final String USER_ALREADY_ACTIVE_MESSAGE = "The user is already active.";
        public static final String RECOVER_USER_SUCCESS_MESSAGE = "User successfully recovered.";
        public static final String RECOVER_USER_FAIL_MESSAGE = "Failed to recover user.";
        public static final String EMPTY_LOCATION_NAME = "Location name cannot be empty";
        public static final String EMPTY_BLOG_TITLE = "Title cannot be empty";
        public static final String EMPTY_BLOG_DESCRIPTION = "Description cannot be empty";
        public static final String EMPTY_BLOG_CONTENT = "Content cannot be empty";
        public static final String EMPTY_LOCATION_DESCRIPTION = "Location description cannot be empty";
        public static final String EMPTY_LOCATION_IMAGE = "Location image cannot be empty";
        public static final String EMPTY_LOCATION_GEO_POSITION = "Location geography position cannot be empty";
        public static final String EXISTED_LOCATION = "Location is existed";
        public static final String CREATE_LOCATION_SUCCESS = "Create location successfully";
        public static final String CREATE_LOCATION_FAIL = "Create location fail";
        public static final String BLOG_NOT_FOUND = "Blog not found";
        public static final String UPDATE_BLOG_SUCCESS_MESSAGE = "Blog successfully updated.";
        public static final String UPDATE_BLOG_FAIL_MESSAGE = "Blog failed to update.";
        public static final String DELETE_BLOG_SUCCESS_MESSAGE = "Blog successfully deleted.";

        public static final String GET_LOCATION_SUCCESS = "Get location successfully";
        public static final String GET_LOCATION_FAIL = "Get location fail";
        public static final String ROLES_RETRIEVED_SUCCESS_MESSAGE = "Get roles successfully";
        public static final String ROLES_RETRIEVED_FAIL_MESSAGE = "Get roles failed";
        public static final String CREATE_SERVICE_PROVIDER_SUCCESS = "Create service provider successfully";
        public static final String CREATE_SERVICE_PROVIDER_FAIL = "Create service provider fail";
        public static final String UPDATE_SERVICE_PROVIDER_SUCCESS = "Update service provider successfully";
        public static final String UPDATE_SERVICE_PROVIDER_FAIL = "Update service provider fail";

        public static final String GET_TAGS_NOT_FOUND_MESSAGE = "Get tags failed";
        public static final String BLOG_RETRIEVED_SUCCESS_MESSAGE = "Blog retrieved successfully";
        public static final String BLOG_RETRIEVED_FAIL_MESSAGE = "Blog retrieved failed";
        public static final String EMPTY_PRICE = "Price can not be empty";
        public static final String EMPTY_LOCATION = "Location can not be empty";
        public static final String EMPTY_ACTIVITY_CATEGORY = "Activity category can not be empty";
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
