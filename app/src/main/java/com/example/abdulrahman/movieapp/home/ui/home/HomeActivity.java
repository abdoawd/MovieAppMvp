package com.example.abdulrahman.movieapp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.view.DetailsFragment;
import com.example.abdulrahman.movieapp.home.ui.moviedetail.view.TabletActivity;
import com.example.abdulrahman.movieapp.home.ui.movieslist.view.MoviesFragment;
import com.example.abdulrahman.movieapp.home.utils.PrefUtils;

/**
 * this activity we can call it the splasActivity
 * it take some Lengths and calculate the area of the device
 * to know if is a tablet or not
 */
public class HomeActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);
        //TODO Remove this
        if (isTablet()) {
            PrefUtils.setIsTablet(true);
            startActivity(new Intent(this, TabletActivity.class));
            finish();
        } else {
            PrefUtils.setIsTablet(false);
            getSupportFragmentManager().beginTransaction().add(R.id.container_main, new MoviesFragment()).commit();
        }
    }

    public boolean isTablet() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6) {
            return true;
        } else  {
            return false;
        }
    }



}