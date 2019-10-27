package com.belajar.mymoviecatalogueuiux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMovies implements Parcelable {
    private int id;
    private String movieTitle;
    private String movieDateRelease;
    private String movieImage;

    public FavoriteMovies(int id, String movieTitle, String movieDateRelease, String movieImage){
        this.id = id;
        this.movieDateRelease = movieDateRelease;
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDateRelease() {
        return movieDateRelease;
    }

    public void setMovieDateRelease(String movieDateRelease) {
        this.movieDateRelease = movieDateRelease;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieDateRelease);
        dest.writeString(this.movieImage);
    }

    public FavoriteMovies() {
    }

    protected FavoriteMovies(Parcel in) {
        this.id = in.readInt();
        this.movieTitle = in.readString();
        this.movieDateRelease = in.readString();
        this.movieImage = in.readString();
    }

    public static final Parcelable.Creator<FavoriteMovies> CREATOR = new Parcelable.Creator<FavoriteMovies>() {
        @Override
        public FavoriteMovies createFromParcel(Parcel source) {
            return new FavoriteMovies(source);
        }

        @Override
        public FavoriteMovies[] newArray(int size) {
            return new FavoriteMovies[size];
        }
    };
}
