package com.example.abdulrahman.movieapp.home.ui.movieslist.presenter;

/**
 * Created by abdulrahman on 6/5/2018.
 */

public interface HomePresenter {

    void onMostPopularMoviesClicked();

    void init();

    void onFavouriteMoviesClicked();

    void onTopRatedClicked();

    void clear();
}
