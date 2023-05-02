package com.example.skincancerdetectiion;

import android.content.Context;
import android.content.SharedPreferences;

public class preferenceManager {

    private static final String PREFS_NAME = "MyPrefs";
    private SharedPreferences sharedPreferences;

    public preferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }
public void logout(){
    // Create an editor instance
    SharedPreferences.Editor editor = sharedPreferences.edit();
    // Add more methods for other data types as needed
    editor.remove(constants.NAME);
    editor.remove(constants.GMAIL);
    editor.apply();
}
}
