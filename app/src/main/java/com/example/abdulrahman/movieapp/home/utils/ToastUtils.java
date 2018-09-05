package com.example.abdulrahman.movieapp.home.utils;

import android.widget.Toast;

import com.example.abdulrahman.movieapp.home.applicatoin.MoviesApplication;

/**
 * Created by abdulrahman on 6/19/2018.
 */

public class ToastUtils {
    public static void showErrorConnecrionToast() {
        Toast.makeText(MoviesApplication.getContext(), "oops check internet connection ", Toast.LENGTH_LONG).show();
    }

    public static void showToast(String message) {
        Toast.makeText(MoviesApplication.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(int strId) {
        Toast.makeText(MoviesApplication.getContext(), strId, Toast.LENGTH_LONG).show();
    }


}
