package com.example.abdulrahman.movieapp.home.ui.movieslist.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.view.DetailsFragment;
import com.example.abdulrahman.movieapp.home.ui.movieslist.presenter.HomePresenter;
import com.example.abdulrahman.movieapp.home.ui.movieslist.presenter.HomePresenterImpl;
import com.example.abdulrahman.movieapp.home.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/19/2018.
 */

public class MoviesFragment extends Fragment implements HomeView {

    private RecyclerView recyclerView;
    private HomePresenter presenter;
    private MoviesAdapter adapter;
    private CommunicationInterface communicationInterface;
    private ConstraintLayout constraintLayoutMoviesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_home);
        constraintLayoutMoviesList = view.findViewById(R.id.constraintLayoutMoviesList);
        presenter = new HomePresenterImpl(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MoviesAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.most_popular) {
            presenter.onMostPopularMoviesClicked();
        } else if (id == R.id.top_rated) {
            presenter.onTopRatedClicked();
        } else if (id == R.id.favourite_movie) {
            presenter.onFavouriteMoviesClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to make the option menue  appear
        // needed in fragment ot the activity
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            List<Movie> movies = savedInstanceState.getParcelableArrayList(LIST_ITEMS);
            if (movies != null)
                adapter.setItems(movies);
        } else {
            presenter.init();
        }

    }

    public void selectMovie(Movie movie) {
        communicationInterface = (CommunicationInterface) getActivity();
        communicationInterface.onMovieSelected(movie);
    }

    public void addDetailsFragment(Movie movie) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_main, DetailsFragment.getInstance(movie));
        fragmentTransaction.addToBackStack("add");
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        presenter.clear();
        super.onDestroy();
    }


    @Override
    public void setMoviesList(List<Movie> movies) {
        // get movies and set the recycler adapter
        adapter.setItems(movies);

    }

    @Override
    public void showConnectionError() {
        Snackbar.make(constraintLayoutMoviesList, R.string.connection_error, Snackbar.LENGTH_LONG).show();
        ToastUtils.showToast(R.string.connection_error);
    }

    @Override
    public void showUnknownError() {
        Snackbar.make(constraintLayoutMoviesList, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
    }


    public interface CommunicationInterface {
        // to send data into the details if we are deleaig with tablet
        void onMovieSelected(Movie movie);
    }


}