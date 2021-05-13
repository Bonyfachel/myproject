package com.example.vkapiexample;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private final static String SHARED_NAME = "global";
    private final String ACCESS_TOKEN_KEY = "access_token";

    public void saveToken(String accessToken) {
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_NAME, MODE_PRIVATE).edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public String loadToken() {
        SharedPreferences prefs = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        return prefs.getString(ACCESS_TOKEN_KEY, "");
    }

    public boolean hasToken() {
        return getSharedPreferences(SHARED_NAME, MODE_PRIVATE).contains(ACCESS_TOKEN_KEY);
    }

}
