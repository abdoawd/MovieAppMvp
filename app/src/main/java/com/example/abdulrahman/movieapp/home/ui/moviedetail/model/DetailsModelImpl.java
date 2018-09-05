package com.example.abdulrahman.movieapp.home.ui.moviedetail.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.abdulrahman.movieapp.home.applicatoin.MoviesApplication;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.base.beans.Video;
import com.example.abdulrahman.movieapp.home.data.FavouriteDataSource;
import com.example.abdulrahman.movieapp.home.data.MovieScheme;
import com.example.abdulrahman.movieapp.home.data.MoviesContentprovider;
import com.example.abdulrahman.movieapp.home.utils.Constants;
import com.example.abdulrahman.movieapp.home.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulrahman on 6/30/2018.
 */

public class DetailsModelImpl implements DetailsModel {
    FavouriteDataSource dataSource;
    List<Video> videos = new ArrayList<>();
    ArrayList<String> reviewsList = new ArrayList();
    Integer id;
    getMoviesVideosCalback calback;
    getReviewsCalback reviewsCalback;
    VideosAsyncTask videosAsyncTask ;
    ReviewsAsyncTask reviewsAsyncTask ;

    public DetailsModelImpl() {
        dataSource = new FavouriteDataSource();
    }

    @Override
    public void addMovieToFavourite(Movie movie, AddMovieToDbCalback calback) {
        if (dataSource.getMovieByID(Long.valueOf(movie.getId()))) {
            //movie is already favourite
            // we need to remove it

           MoviesApplication.getContext().getContentResolver().delete(MoviesContentprovider.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build(), null, null);

            calback.removeMovie();

        } else {
//            dataSource.addMovieToDB(movie.getId(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(),
//                    movie.getReleaseDate(), movie.getVoteCount(), movie.getVoteAverage());
            ContentValues values= new ContentValues();
            values.put(MovieScheme.COLUMN_ID_KEY, movie.getId());
            values.put(MovieScheme.COLUMN_MOVIE_NMAE_KEY, movie.getOriginalTitle());
            values.put(MovieScheme.COLUMN_OVERVIEW_KEY, movie.getOverview());
            values.put(MovieScheme.COLUMN_POSTER_PATH_KEY, movie.getPosterPath());
            values.put(MovieScheme.COLUMN_RELASE_DATE_KEY, movie.getReleaseDate());
            values.put(MovieScheme.COLUMN_VOTE_COUNT_KEY, movie.getVoteCount());
            values.put(MovieScheme.COLUMN_VOTE_AVERAGE_KEY, movie.getVoteAverage());
            Uri uri= MoviesApplication.getContext().getContentResolver().insert(MoviesContentprovider.CONTENT_URI,values);
            calback.addMovieSuccessfullyToDB();


        }
    }

    @Override
    public void getMoviesVideos(Integer id, getMoviesVideosCalback calback) {
        this.id = id;
        this.calback = calback;
         videosAsyncTask= new VideosAsyncTask();
        videosAsyncTask.execute();
    }

    @Override
    public void getReviewsList(getReviewsCalback calback) {
        this.reviewsCalback = calback;
       reviewsAsyncTask= new ReviewsAsyncTask();
       reviewsAsyncTask.execute();

    }

    @Override
    public boolean isFavourite(Integer id) {
        return dataSource.getMovieByID(Long.valueOf(id));
    }

    @Override
    public void cancelAllRequests() {
        if (reviewsAsyncTask!=null){
            reviewsAsyncTask.cancel(true);
        }
        if (videosAsyncTask!=null){
            videosAsyncTask.cancel(true);
        }

    }


    public class VideosAsyncTask extends AsyncTask<String, Void, List<Video>> {
        @Override
        protected List<Video> doInBackground(String... params) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            String tsonstring;

            try {

                URL url1 = new URL(Constants.BASE_URL + id + Constants.VIDEOS_Url_KEY + Constants.API_KEY
                );
                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod(Constants.GET_REQUEST_TYPE);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                }
                tsonstring = buffer.toString();
                return pars(tsonstring);
            } catch (IOException e) {

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Video> videos) {
            super.onPostExecute(videos);
            calback.getVideosSuccessfully(videos);
        }

        public List<Video> pars(String json) throws JSONException {
            videos.clear();
            JSONObject all = new JSONObject(json);
            JSONArray myArray = all.getJSONArray(Constants.RESULTS_RESPONS_KEY);
            JSONObject item;
            Video video;
            for (int i = 0; i < myArray.length(); i++) {
                item = myArray.getJSONObject(i);
                video = new Video(item.getString(Constants.NAME_KEY), item.getString(Constants.KEY_RESPONSE_KEY));
                videos.add(video);
            }
            return videos;
        }
    }

    public class ReviewsAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            HttpURLConnection urlConnection;
            BufferedReader reader;
            String tsonstring;
            try {
                URL url1 = new URL(Constants.BASE_URL + id + Constants.REVIEWS_Url_KEY + Constants.API_KEY
                );

                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod(Constants.GET_REQUEST_TYPE);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    tsonstring = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                }
                tsonstring = buffer.toString();
                return pars(tsonstring);
            } catch (IOException e) {

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> string) {
            super.onPostExecute(string);
            reviewsCalback.onGettingREviews(string);
        }

        public ArrayList<String> pars(String json) throws JSONException {
            reviewsList.clear();
            JSONObject all = new JSONObject(json);
            JSONArray myArray = all.getJSONArray(Constants.RESULTS_RESPONS_KEY);
            for (int i = 0; i < myArray.length(); i++) {
                JSONObject item = myArray.getJSONObject(i);

                String s = item.getString(Constants.CONTENT_RESPONSE_KEY);
                reviewsList.add(s);
            }
            return reviewsList;
        }
    }

}
