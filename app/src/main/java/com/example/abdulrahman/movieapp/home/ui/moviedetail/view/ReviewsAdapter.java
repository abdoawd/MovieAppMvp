package com.example.abdulrahman.movieapp.home.ui.moviedetail.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdulrahman.movieapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 7/3/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    List<String> list;

    public ReviewsAdapter() {
        list=new ArrayList<>();
    }
    public void setItems(List<String> items){
        this.list=items ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bindReview(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvReview = itemView.findViewById(R.id.tv_row_reviews);
        }

        public void bindReview(int position) {
            tvReview.setText(list.get(position).toString());

        }
    }
}
