package com.example.taskdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSharedPreferences {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String APPLICATION_PREFERENCES_NAME = "UserAuthPrefs";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";


    public ApplicationSharedPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    // Method to save the token
    public void saveToken(String token) {
        editor.putString("token", token);
        editor.apply();
    }

    // Method to retrieve the token
    public String getToken() {
        return sharedPreferences.getString("token", null);
    }


// Method to save login status
public void setIsLoggedIn(boolean isLoggedIn) {
    editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
    editor.apply();
}

    // Method to retrieve login status
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }
}
