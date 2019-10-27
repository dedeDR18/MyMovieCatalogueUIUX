package com.belajar.mymoviecatalogueuiux.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIETITLE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIEIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.DATERELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TABLE_NAME;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TABLE_NAME_TVSHOW;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWRELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWTITLE;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
            + "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContract.MovieColumns._ID,
            MOVIETITLE,
            MOVIEIMAGE,
            DATERELEASE
    );

    private static final String SQL_CREATE_TABLE_FAVORITE_TVSHOW = String.format("CREATE TABLE %s"
                    + "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME_TVSHOW,
            DatabaseContract.TvshowColumns._ID,
            TVSHOWTITLE,
            TVSHOWIMAGE,
            TVSHOWRELEASE
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TVSHOW);
        onCreate(db);
    }
}
