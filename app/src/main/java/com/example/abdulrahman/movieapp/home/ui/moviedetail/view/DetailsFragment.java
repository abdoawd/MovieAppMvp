package com.example.abdulrahman.movieapp.home.ui.moviedetail.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.base.beans.Video;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.presenter.DetailsPresenterImpl;
import com.example.abdulrahman.movieapp.home.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/19/2018.
 */

public class DetailsFragment extends Fragment implements View.OnClickListener, DetailsView {
    private static final String MOVIE_KEY = "movie";
    DetailsPresenterImpl presenter;
    VideoAdapter videoAdapter;
    ReviewsAdapter reviewsAdapter;
    ImageView imageViewMovie;
    TextView tvName, tvVoteAverage, tvReleaseDate, tvOverView;
    Movie movie;
    FloatingActionButton fabAddMovieToDb;
    RecyclerView recyclerViewVideos, recyclerViewReviews;

    public static DetailsFragment getInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewMovie = view.findViewById(R.id.detials_image);
        tvName = view.findViewById(R.id.movie_name);
        tvVoteAverage = view.findViewById(R.id.vote_average);
        tvReleaseDate = view.findViewById(R.id.relase_date_tv);
        tvOverView = view.findViewById(R.id.over_view_lable);

        recyclerViewVideos = view.findViewById(R.id.recycler_trailer);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewReviews = view.findViewById(R.id.recycler_reviews);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabAddMovieToDb = view.findViewById(R.id.fab_add_movie_to_favourite);
        fabAddMovieToDb.setOnClickListener(this);

        presenter = new DetailsPresenterImpl(this);

        videoAdapter = new VideoAdapter(this);
        recyclerViewVideos.setAdapter(videoAdapter);

        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_KEY);
            presenter.isFavourite(movie.getId());
            presenter.getMovieTrailers(movie.getId());
            presenter.getMovieReviews();
            tvVoteAverage.setText(movie.getVoteAverage());
            tvName.setText(movie.getOriginalTitle());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvOverView.setText(movie.getOverview());
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .resize(500, 500).into(imageViewMovie);
        }
    }

    public void getMOvie(Movie movie) {
        if (movie != null) {
            presenter.getMovieTrailers(movie.getId());
            presenter.getMovieReviews();
            tvVoteAverage.setText(movie.getVoteAverage().toString());
            tvName.setText(movie.getOriginalTitle());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvOverView.setText(movie.getOverview());
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .resize(500, 500).into(imageViewMovie);
        }

    }

    @Override
    public void onClick(View v) {
        presenter.onFavouriteClicked(movie);


    }

    @Override
    public void onDestroy() {
        presenter.clear();
        super.onDestroy();
    }

    @Override
    public void addToDbSuccessfully() {
        //TODO use snackbar
        fabAddMovieToDb.setImageDrawable(getResources().getDrawable(R.drawable.favor));
        ToastUtils.showToast(R.string.add_movie_success);

    }

    @Override
    public void removeFromDB() {
        //TODO use snackbar
        fabAddMovieToDb.setImageDrawable(getResources().getDrawable(R.drawable.unfavor));
        ToastUtils.showToast(R.string.movie_removed_from_db);

    }

    @Override
    public void setVideosList(List<Video> videos) {
        videoAdapter.setItems(videos);

    }

    @Override
    public void setReviewsList(ArrayList<String> list) {

        reviewsAdapter.setItems(list);

    }

    @Override
    public void movieIsFavourite() {
        fabAddMovieToDb.setImageDrawable(getResources().getDrawable(R.drawable.favor));
    }
}