package com.poc.intuition.service;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionService {

    private SharedPreferences preferencesStorage;
    private String preferencesPackage = "com.poc.intuition";
    private final static String USERNAME_KEY = "logged_in_user";
    private final static String SETUP_FOR_USER_KEY = "user_onboarding_";

    public UserSessionService(Context context) {
        preferencesStorage = context.getSharedPreferences(preferencesPackage, Context.MODE_PRIVATE);
    }

    public String loggedInUsername() {
        return preferencesStorage.getString(USERNAME_KEY, null);
    }

    public void loginUser(String username) {
        preferencesStorage.edit().putString(USERNAME_KEY, username.toLowerCase()).commit();
    }

    public void markUserOnboardingComplete() {
        preferencesStorage.edit().putBoolean(SETUP_FOR_USER_KEY+loggedInUsername().toLowerCase(), true).commit();
    }

    public boolean isUserOnboardingComplete() {
        return preferencesStorage.getBoolean(SETUP_FOR_USER_KEY+loggedInUsername().toLowerCase(), false);
    }


}
