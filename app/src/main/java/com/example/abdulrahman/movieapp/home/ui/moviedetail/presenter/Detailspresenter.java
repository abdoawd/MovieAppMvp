package com.example.abdulrahman.movieapp.home.ui.moviedetail.presenter;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;

/**
 * Created by abdulrahman on 6/30/2018.
 */

public interface Detailspresenter {
    void onFavouriteClicked(Movie movie);

    void getMovieTrailers(Integer id);

    void getMovieReviews();

    void isFavourite(Integer id);

    void clear();
}
