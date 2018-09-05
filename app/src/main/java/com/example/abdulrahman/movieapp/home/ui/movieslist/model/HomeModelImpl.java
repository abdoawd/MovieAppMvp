package com.example.abdulrahman.movieapp.home.ui.movieslist.model;

import android.database.Cursor;

import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.data.FavouriteDataSource;
import com.example.abdulrahman.movieapp.home.data.MovieScheme;
import com.example.abdulrahman.movieapp.home.ui.movieslist.async.MoviesAsyncTask;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by abdulrahman on 6/5/2018.
 */

public class HomeModelImpl implements HomeModel, MoviesAsyncTask.MoviesAsyncTaskCallback {
    private OnGettingMoviesCallback callback;
    private MoviesAsyncTask moviesAsyncTask;


    @Override
    public void getMoviesList(final OnGettingMoviesCallback callback, int sortedMovies) {
        moviesAsyncTask = new MoviesAsyncTask(this);
        this.callback = callback;
        moviesAsyncTask.execute(sortedMovies);
    }

    @Override
    public void getFavouritesMovies(OnGettingMoviesCallback callback) {
        this.callback = callback;
        List<Movie> movies = new ArrayList<>();
        FavouriteDataSource dataSource = new FavouriteDataSource();
        Movie movie;
        Cursor cursor = dataSource.getAllFavourite();
        while (cursor.moveToNext()) {
            movie = new Movie(cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_POSTER_PATH_KEY)),
                    cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_OVERVIEW_KEY)),
                    cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_RELASE_DATE_KEY)),
                    cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_MOVIE_NMAE_KEY)),
                    Integer.valueOf((cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_ID_KEY)))),
                    cursor.getString(cursor.getColumnIndex(MovieScheme.COLUMN_VOTE_AVERAGE_KEY)));
            movies.add(movie);
        }
        callback.onGetMoviesList(movies);
    }
    @Override
    public void cancelAllRequests() {
        if (moviesAsyncTask != null) {
            moviesAsyncTask.cancel(true);
        }
    }

    @Override
    public void onGettingMoviesFailure(Exception e) {
        // TODO make use case for handling exception
        callback.onGettingMoviesFailure(e);
    }

    @Override
    public void onGettingMoviesSuccess(List<Movie> movies) {
        callback.onGetMoviesList(movies);
    }
}
