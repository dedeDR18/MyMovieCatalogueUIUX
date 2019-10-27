package com.belajar.mymoviecatalogueuiux.helper;

import android.database.Cursor;


import com.belajar.mymoviecatalogueuiux.model.FavoriteTvshow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWRELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWTITLE;

public class MappingHelperTvshow {
    public static ArrayList<FavoriteTvshow> mapCursorToArrayList(Cursor tvshowsFavCursor) {
        ArrayList<FavoriteTvshow> tvshowsFavList = new ArrayList<>();
        while (tvshowsFavCursor.moveToNext()) {
            int id = tvshowsFavCursor.getInt(tvshowsFavCursor.getColumnIndexOrThrow(_ID));
            String title = tvshowsFavCursor.getString(tvshowsFavCursor.getColumnIndexOrThrow(TVSHOWTITLE));
            String date = tvshowsFavCursor.getString(tvshowsFavCursor.getColumnIndexOrThrow(TVSHOWRELEASE));
            String image = tvshowsFavCursor.getString(tvshowsFavCursor.getColumnIndexOrThrow(TVSHOWIMAGE));
            tvshowsFavList.add(new FavoriteTvshow(id, title, date, image));
        }
        return tvshowsFavList;
    }
}
