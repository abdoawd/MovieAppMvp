package com.example.abdulrahman.movieapp.home.ui.moviedetail.presenter;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.base.beans.Video;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.model.DetailsModel;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.model.DetailsModelImpl;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.view.DetailsView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/30/2018.
 */

public class DetailsPresenterImpl implements Detailspresenter, DetailsModel.AddMovieToDbCalback,
        DetailsModel.getMoviesVideosCalback, DetailsModel.getReviewsCalback {
    DetailsModelImpl model;
    private WeakReference<DetailsView> viewReffrense;

    public DetailsPresenterImpl(DetailsView reference) {
        this.viewReffrense = new WeakReference<>(reference);
        model = new DetailsModelImpl();
    }

    @Override
    public void onFavouriteClicked(Movie movie) {
        model.addMovieToFavourite(movie, this);


    }

    @Override
    public void getMovieTrailers(Integer id) {
        model.getMoviesVideos(id, this);

    }

    @Override
    public void getMovieReviews() {
        model.getReviewsList(this);

    }

    @Override
    public void isFavourite(Integer id) {
        if (model.isFavourite(id)) {
            viewReffrense.get().movieIsFavourite();

        }


    }

    @Override
    public void clear() {
        model.cancelAllRequests();
        viewReffrense.clear();
        viewReffrense = null;

    }

    @Override
    public void addMovieSuccessfullyToDB() {
        viewReffrense.get().addToDbSuccessfully();

    }


    @Override
    public void removeMovie() {
        viewReffrense.get().removeFromDB();

    }

    @Override
    public void getVideosSuccessfully(List<Video> videos) {
        if (videos == null) {

        } else {
            viewReffrense.get().setVideosList(videos);
        }
    }

    @Override
    public void onGettingREviews(ArrayList<String> list) {
        if (list == null) {

        } else {
            viewReffrense.get().setReviewsList(list);

        }
    }

}
