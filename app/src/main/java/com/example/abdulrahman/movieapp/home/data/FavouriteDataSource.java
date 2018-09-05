package com.example.abdulrahman.movieapp.home.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.abdulrahman.movieapp.home.applicatoin.MoviesApplication;
import com.example.abdulrahman.movieapp.home.utils.ToastUtils;

/**
 * Created by abdulrahman on 6/29/2018.
 */

public class FavouriteDataSource extends SQLiteOpenHelper {

    String createDB = "CREATE TABLE " + MovieScheme.TABLEE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MovieScheme.COLUMN_ID_KEY + " INTEGER , "
            + MovieScheme.COLUMN_MOVIE_NMAE_KEY + " TEXT ,"
            + MovieScheme.COLUMN_OVERVIEW_KEY + " TEXT ,"
            + MovieScheme.COLUMN_POSTER_PATH_KEY + " TEXT ,"
            + MovieScheme.COLUMN_RELASE_DATE_KEY + " TEXT ,"
            + MovieScheme.COLUMN_VOTE_COUNT_KEY + " TEXT ,"
            + MovieScheme.COLUMN_VOTE_AVERAGE_KEY + " TEXT )";

    public FavouriteDataSource() {
        super(MoviesApplication.getContext(), MovieScheme.DATABASE_NAME, null, MovieScheme.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);

    }

    public void addMovieToDB(long id, String name, String overview, String poster_path, String release_date,
                             String vote_count, String vote_average) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieScheme.COLUMN_ID_KEY, id);
        values.put(MovieScheme.COLUMN_MOVIE_NMAE_KEY, name);
        values.put(MovieScheme.COLUMN_OVERVIEW_KEY, overview);
        values.put(MovieScheme.COLUMN_POSTER_PATH_KEY, poster_path);
        values.put(MovieScheme.COLUMN_RELASE_DATE_KEY, release_date);
        values.put(MovieScheme.COLUMN_VOTE_COUNT_KEY, vote_count);
        values.put(MovieScheme.COLUMN_VOTE_AVERAGE_KEY, vote_average);
      //  long l = sqLiteDatabase.insert(MovieScheme.TABLEE_NAME, null, values);
        Uri uri= MoviesApplication.getContext().getContentResolver().insert(MoviesContentprovider.CONTENT_URI,values);
        if (uri!=null){
            ToastUtils.showToast(uri.toString());
        }

    }

    public boolean getMovieByID(Long id) {
        String s = " select * from " + MovieScheme.TABLEE_NAME + " where " + MovieScheme.COLUMN_ID_KEY + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor= MoviesApplication.getContext().getContentResolver().query(MoviesContentprovider.CONTENT_URI,null,null,null,null);
        Cursor cursor = db.rawQuery(s, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Cursor getAllFavourite() {
        Cursor cursor= MoviesApplication.getContext().getContentResolver().query(MoviesContentprovider.CONTENT_URI,null,null,null,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + MovieScheme.TABLEE_NAME);
        onCreate(db);
    }


    public void removeMovieFromDB(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MovieScheme.TABLEE_NAME, MovieScheme.COLUMN_ID_KEY + "=" + id, null);
    }
}