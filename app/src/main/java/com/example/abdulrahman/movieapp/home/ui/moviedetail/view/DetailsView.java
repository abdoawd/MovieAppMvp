package com.example.abdulrahman.movieapp.home.ui.moviedetail.view;

import com.example.abdulrahman.movieapp.home.base.beans.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/30/2018.
 */

public interface DetailsView {
    void addToDbSuccessfully();

    void removeFromDB();

    void setVideosList(List<Video> videos);

    void setReviewsList(ArrayList<String> list);

    void movieIsFavourite();
}
