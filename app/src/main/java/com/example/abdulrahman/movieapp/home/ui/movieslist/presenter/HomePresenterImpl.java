package com.example.abdulrahman.movieapp.home.ui.movieslist.presenter;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.ui.movieslist.model.HomeModel;
import com.example.abdulrahman.movieapp.home.ui.movieslist.model.HomeModelImpl;
import com.example.abdulrahman.movieapp.home.ui.movieslist.view.HomeView;
import com.example.abdulrahman.movieapp.home.utils.Constants;
import com.example.abdulrahman.movieapp.home.utils.NetworkUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by abdulrahman on 6/5/2018.
 */

public class HomePresenterImpl implements HomePresenter, HomeModel.OnGettingMoviesCallback {
    private HomeModel model;
    private WeakReference<HomeView> viewReference;


    public HomePresenterImpl(HomeView homeView) {
        viewReference = new WeakReference<>(homeView);
        model = new HomeModelImpl();
    }


    public HomeView getView() {
        return viewReference.get();
    }

    @Override
    public void onMostPopularMoviesClicked() {
        if (NetworkUtils.isOnline()) {
            model.getMoviesList(this, Constants.MOVIES_LIST_TYPE_MOST_POPULAR);
        } else {
            getView().showConnectionError();
        }
    }

    @Override
    public void init() {
        onTopRatedClicked();
    }

    @Override
    public void onFavouriteMoviesClicked() {
        model.getFavouritesMovies(this);

    }

    @Override
    public void onGetMoviesList(List<Movie> movies) {
        if (isViewCleared()) {
            return;
        }
        getView().setMoviesList(movies);

    }

    @Override
    public void onGettingMoviesFailure(Exception e) {
        if (isViewCleared()) {
            return;
        }
        if (e instanceof JSONException) {
            getView().showUnknownError();
        } else {
            getView().showConnectionError();
        }
    }

    @Override
    public void onTopRatedClicked() {
        if (NetworkUtils.isOnline()) {
            model.getMoviesList(this, Constants.MOVIES_LIST_TYPE_TOP_RATED);
        } else {
            getView().showConnectionError();
        }
    }

    @Override
    public void clear() {
        model.cancelAllRequests();
        viewReference.clear();
        viewReference = null;
    }

    private boolean isViewCleared() {
        return viewReference == null;
    }


}
