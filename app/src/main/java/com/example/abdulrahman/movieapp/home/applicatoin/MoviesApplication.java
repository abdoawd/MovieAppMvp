package com.example.abdulrahman.movieapp.home.applicatoin;

import android.app.Application;
import android.content.Context;

/**
 * api_key=1b5062dd05eaace07a10010e59798def
 */
public class MoviesApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
