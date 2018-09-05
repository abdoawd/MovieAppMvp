package com.example.abdulrahman.movieapp.home.ui.movieslist.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.utils.PrefUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by abdulrahman on 6/4/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ImageViewHolder> {
    private List<Movie> movies;
    private MoviesFragment fragment;

    public MoviesAdapter(MoviesFragment fragment) {
        movies = new ArrayList<>();
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bindImage(position);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getItems() {
        return movies;
    }

    public void setItems(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imvHome;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imvHome = itemView.findViewById(R.id.imgMovie);
            itemView.setOnClickListener(this);
        }

        public void bindImage(int position) {
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movies.get(position).getPosterPath())
                    .resize(500, 500).into(imvHome);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() < 0)
                return;
            if (PrefUtils.isTablet()) {
                fragment.selectMovie(movies.get(getAdapterPosition()));
            } else {
                fragment.addDetailsFragment(movies.get(getAdapterPosition()));

            }
        }
    }
}
