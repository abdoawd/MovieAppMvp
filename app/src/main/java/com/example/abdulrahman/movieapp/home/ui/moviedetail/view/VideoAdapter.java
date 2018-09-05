package com.example.abdulrahman.movieapp.home.ui.moviedetail.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 7/1/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideosViewHolder> {
    List<Video> videos;
    VideosClickListner videosClickListner;
    DetailsFragment fragment ;
    public VideoAdapter(DetailsFragment fragment ){
        videos=new ArrayList<>();
        this.fragment=fragment;

    }

    public VideoAdapter(List<Video> videos, VideosClickListner videosClickListner) {
        this.videos = videos;
        this.videosClickListner = videosClickListner;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_videos, parent, false);
        VideosViewHolder viewHolder = new VideosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        holder.bindText(position);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    interface VideosClickListner {
        void onVideosClick(int listItemClick);
    }
    public void setItems(List<Video> items) {
        this.videos = items;
        notifyDataSetChanged();
    }
    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvVideos;

        public VideosViewHolder(View itemView) {
            super(itemView);
            tvVideos = itemView.findViewById(R.id.tv_row_videos);
            itemView.setOnClickListener(this);
        }

        public void bindText(int position) {
            tvVideos.setText(videos.get(position).getName());

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videos.get(getAdapterPosition()).getKey()));
           fragment. startActivity(Intent.createChooser(intent, R.string.share_movie + " "));

        }
    }
}
