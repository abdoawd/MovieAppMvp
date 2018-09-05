package com.example.abdulrahman.movieapp.home.ui.moviedetail.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.ui.movieslist.view.MoviesFragment;

public class TabletActivity extends AppCompatActivity implements MoviesFragment.CommunicationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        FragmentManager manager = getSupportFragmentManager();
        DetailsFragment fragment = (DetailsFragment) manager.findFragmentById(R.id.details_fragment);
        fragment.getMOvie(movie);
    }
}
