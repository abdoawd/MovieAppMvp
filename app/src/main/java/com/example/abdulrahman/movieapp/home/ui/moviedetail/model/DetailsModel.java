package com.example.abdulrahman.movieapp.home.ui.moviedetail.model;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.base.beans.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/30/2018.
 */

public interface DetailsModel {
    void addMovieToFavourite(Movie movie, AddMovieToDbCalback calback);

    void getMoviesVideos(Integer id, getMoviesVideosCalback calback);

    void getReviewsList(getReviewsCalback calback);

    boolean isFavourite(Integer id);

    void cancelAllRequests();

    interface AddMovieToDbCalback {
        void addMovieSuccessfullyToDB();


        void removeMovie();
    }

    interface getMoviesVideosCalback {
        void getVideosSuccessfully(List<Video> videos);
    }

    interface getReviewsCalback {
        void onGettingREviews(ArrayList<String> list);
    }
}
