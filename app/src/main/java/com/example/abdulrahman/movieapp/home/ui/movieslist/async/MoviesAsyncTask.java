package com.example.abdulrahman.movieapp.home.ui.movieslist.async;

import android.os.AsyncTask;

import com.example.abdulrahman.movieapp.R;
import com.example.abdulrahman.movieapp.home.base.beans.Movie;
import com.example.abdulrahman.movieapp.home.utils.Constants;

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
 * Created by abdulrahman on 7/7/2018.
 */

public class MoviesAsyncTask extends AsyncTask<Integer, Void, Object> {

    private MoviesAsyncTaskCallback callback;

    public MoviesAsyncTask(MoviesAsyncTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Integer... params) {
        int moviesListSortedType = params[0];
        HttpURLConnection urlConnection;
        BufferedReader reader;
        URL url;
        Exception exceptionError;
        try {
            if (moviesListSortedType == Constants.MOVIES_LIST_TYPE_TOP_RATED) {
                url = new URL(Constants.BASE_URL+Constants.TopRatedUrl_KEY+Constants.API_KEY);
            } else {
                url = new URL(Constants.BASE_URL+Constants.MOSTPOPULARURL_key+Constants.API_KEY);
            }
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(Constants.GET_REQUEST_TYPE);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            return parseMoviesList(buffer.toString());
        } catch (IOException e) {
            exceptionError = e;
            e.printStackTrace();
        } catch (JSONException e) {
            exceptionError = e;
            e.printStackTrace();
        }
        return exceptionError;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (result instanceof IOException || result instanceof JSONException) {
            callback.onGettingMoviesFailure((Exception) result);
        } else {
            callback.onGettingMoviesSuccess((List<Movie>) result);
        }
    }


    private List<Movie> parseMoviesList(String json) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        JSONObject fullResponseJsonObject = new JSONObject(json);
        JSONArray myArray = fullResponseJsonObject.getJSONArray(Constants.RESULTS_RESPONS_KEY);
        for (int i = 0; i < myArray.length(); i++) {
            JSONObject item = myArray.getJSONObject(i);
            Movie temp = new Movie(item.getString(Constants.POSTER_PATH_KEY),
                    item.getString(Constants.OVER_VIEW_KEY),
                    item.getString(Constants.RELEASE_DATE_KEY), item.getString(Constants.TITLE_KEY)
                    , item.getInt(Constants.ID_KEY), item.getString(Constants.VOTE_AVERAGE_KEY));
            movieList.add(temp);

        }
        return movieList;
    }

    public interface MoviesAsyncTaskCallback {

        void onGettingMoviesFailure(Exception e);

        void onGettingMoviesSuccess(List<Movie> movies);

    }
}
