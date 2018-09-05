package com.example.abdulrahman.movieapp.home.utils;

import android.content.SharedPreferences;

import com.example.abdulrahman.movieapp.home.applicatoin.MoviesApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abdulrahman on 7/5/2018.
 */

public class PrefUtils {

    private static final String IS_TABLET = "is_tablet";

    public static boolean isTablet() {
        SharedPreferences preferences = MoviesApplication.getContext().getSharedPreferences(IS_TABLET, MODE_PRIVATE);
        return  preferences.getBoolean(IS_TABLET, true);
    }

    public static void setIsTablet(boolean isTablet) {
        SharedPreferences preferences = MoviesApplication.getContext().getSharedPreferences(IS_TABLET, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_TABLET, isTablet).commit();
    }

}
