package com.example.abdulrahman.movieapp.home.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.abdulrahman.movieapp.home.applicatoin.MoviesApplication;

/**
 * Created by abdulrahman on 7/5/2018.
 */

public class NetworkUtils {
    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) MoviesApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}