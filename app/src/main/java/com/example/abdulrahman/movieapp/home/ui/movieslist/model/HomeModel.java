package com.example.abdulrahman.movieapp.home.ui.movieslist.model;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;

import java.util.List;

/**
 * Created by abdulrahman on 6/5/2018.
 */

public interface HomeModel {
    void getMoviesList(OnGettingMoviesCallback callback, int sortdKey);

    void getFavouritesMovies(OnGettingMoviesCallback callback);

    void cancelAllRequests();

    interface OnGettingMoviesCallback {
        void onGetMoviesList(List<Movie> movies);

        void onGettingMoviesFailure(Exception e);
    }
}
