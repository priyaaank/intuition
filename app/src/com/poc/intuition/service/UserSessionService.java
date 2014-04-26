package com.poc.intuition.service;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionService {

    private SharedPreferences preferencesStorage;
    private String preferencesPackage = "com.poc.intuition";
    private final static String USERNAME_KEY = "logged_in_user";

    public UserSessionService(Context context) {
        preferencesStorage = context.getSharedPreferences(preferencesPackage, Context.MODE_PRIVATE);
    }

    public String loggedInUsername() {
        return preferencesStorage.getString(USERNAME_KEY, null);
    }

    public void loginUser(String username) {
        preferencesStorage.edit().putString(USERNAME_KEY, username).commit();
    }
}
