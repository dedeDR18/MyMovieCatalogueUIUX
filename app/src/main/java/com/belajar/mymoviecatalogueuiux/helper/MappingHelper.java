package com.belajar.mymoviecatalogueuiux.helper;

import android.database.Cursor;

import com.belajar.mymoviecatalogueuiux.model.FavoriteMovies;
import com.belajar.mymoviecatalogueuiux.model.FavoriteTvshow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.DATERELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIEIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIETITLE;

public class MappingHelper {
    public static ArrayList<FavoriteMovies> mapCursorToArrayList(Cursor moviesFavCursor) {
        ArrayList<FavoriteMovies> moviesFavList = new ArrayList<>();
        while (moviesFavCursor.moveToNext()) {
            int id = moviesFavCursor.getInt(moviesFavCursor.getColumnIndexOrThrow(_ID));
            String title = moviesFavCursor.getString(moviesFavCursor.getColumnIndexOrThrow(MOVIETITLE));
            String date = moviesFavCursor.getString(moviesFavCursor.getColumnIndexOrThrow(DATERELEASE));
            String image = moviesFavCursor.getString(moviesFavCursor.getColumnIndexOrThrow(MOVIEIMAGE));
            moviesFavList.add(new FavoriteMovies(id, title, date, image));
        }
        return moviesFavList;
    }
}

