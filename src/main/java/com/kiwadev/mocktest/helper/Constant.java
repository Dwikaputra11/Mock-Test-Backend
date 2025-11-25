package com.kiwadev.mocktest.helper;


public record Constant() {
    public static final String SUCCESS_RETRIEVE_MSG = "Successfully retrieved data!";
    public static final String SUCCESS_EDIT_MSG = "Successfully edit data!";
    public static final String LOGGED_USER = "user";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    public static final String KEY_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_VIEW_FORMAT = "EEEE,dd MMM yyyy";
    public static final Integer PENDING = 1;
    public static final Integer BEING_VERIFIED = 2;
    public static final Integer REJECTED = 3;
    public static final Integer SUCCESS = 4;
    public static final Integer FAILED = 5;
    public static final Integer CANCELLED = 6;
    public static final Integer NEED_CONFIRMATION = 7;
}

