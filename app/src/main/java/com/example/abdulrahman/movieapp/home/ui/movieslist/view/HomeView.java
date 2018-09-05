package com.example.abdulrahman.movieapp.home.ui.movieslist.view;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;

import java.util.List;

/**
 * Created by abdulrahman on 6/5/2018.
 */

public interface HomeView {
    String LIST_ITEMS = "list items";

    void setMoviesList(List<Movie> movies);

    void showConnectionError();

    void showUnknownError();
}
