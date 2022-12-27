package com.example.online_shop_back.utils;

public interface AppConstant {
    /*REST API Error codes*/
    int INCORRECT_USERNAME_OR_PASSWORD = 3001;
    int EMAIL_OR_PHONE_EXIST = 3002;
    int EXPIRED = 3003;
    int ACCESS_DENIED = 3004;
    int NOT_FOUND = 3005;
    int INVALID = 3006;
    int REQUIRED = 3007;


    int SERVER_ERROR = 3008;
    int CONFLICT = 3009;
    int NO_ITEMS_FOUND = 3011;
    int CONFIRMATION = 3012;
    int USER_NOT_ACTIVE = 3013;
    int JWT_TOKEN_INVALID = 3014;

    String AUTHORIZATION_HEADER = "AUTHORIZATION_HEADER";

    String[] OPEN_PAGES_FOR_ALL_METHOD = {
            "/api/auth/actuator/refresh",
            "/actuator/refresh",
    };

}
