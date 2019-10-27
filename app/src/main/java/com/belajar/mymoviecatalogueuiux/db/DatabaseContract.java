package com.belajar.mymoviecatalogueuiux.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "moviesfav";
    public static String TABLE_NAME_TVSHOW = "tvshowfav";
    public static final class MovieColumns implements BaseColumns{
        public static String MOVIETITLE = "movietitle";
        public static String MOVIEIMAGE = "movieimage";
        public static String DATERELEASE = "daterelease";
    }
    public static final class TvshowColumns implements BaseColumns{
        public static String TVSHOWTITLE = "tvshowtitle";
        public static String TVSHOWIMAGE = "tvshowimage";
        public static String TVSHOWRELEASE = "tvshowrelease";
    }
}
