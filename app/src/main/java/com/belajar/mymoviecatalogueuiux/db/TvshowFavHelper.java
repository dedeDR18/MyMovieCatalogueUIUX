package com.belajar.mymoviecatalogueuiux.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TABLE_NAME_TVSHOW;

public class TvshowFavHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static TvshowFavHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvshowFavHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvshowFavHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvshowFavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor selectByTitle(String title){
        return database.rawQuery("SELECT * FROM tvshowfav WHERE tvshowtitle = ?", new String[] {title});

    }

    public long insert(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
