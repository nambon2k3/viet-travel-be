package com.fpt.capstone.tourism.constants;

public class Constants {
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/tourism" + API_VERSION;

    //API Path Naming
    public static final String USER_PATH = BASE_URL + "/users";


    //Client Endpoint
    public static final String CLIENT_URL = "http://localhost:4200/";



    public static final class UserExceptionInformation {

        public static final String USER_NOT_FOUND_CODE = "000100";
        public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    }


    public static final class Message {
        public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
        public static final String LOGIN_FAIL_MESSAGE = "Login Failed!";
    }


    public static final class Regex {
        public static final String REGX_PASSWORD = "$d{8}^";

    }


}
