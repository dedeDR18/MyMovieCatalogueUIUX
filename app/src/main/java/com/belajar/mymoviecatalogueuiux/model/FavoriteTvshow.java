package com.belajar.mymoviecatalogueuiux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteTvshow implements Parcelable {
    private int id;
    private String tvshowTitle;
    private String tvshowRelease;
    private String tvshowImage;


    public FavoriteTvshow(int id, String tvshowTitle, String tvshowRelease, String tvshowImage){
        this.id = id;
        this.tvshowTitle = tvshowTitle;
        this.tvshowImage = tvshowImage;
        this.tvshowRelease = tvshowRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTvshowTitle() {
        return tvshowTitle;
    }

    public void setTvshowTitle(String tvshowTitle) {
        this.tvshowTitle = tvshowTitle;
    }

    public String getTvshowRelease() {
        return tvshowRelease;
    }

    public void setTvshowRelease(String tvshowRelease) {
        this.tvshowRelease = tvshowRelease;
    }

    public String getTvshowImage() {
        return tvshowImage;
    }

    public void setTvshowImage(String tvshowImage) {
        this.tvshowImage = tvshowImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tvshowTitle);
        dest.writeString(this.tvshowRelease);
        dest.writeString(this.tvshowImage);
    }

    public FavoriteTvshow() {
    }

    protected FavoriteTvshow(Parcel in) {
        this.id = in.readInt();
        this.tvshowTitle = in.readString();
        this.tvshowRelease = in.readString();
        this.tvshowImage = in.readString();
    }

    public static final Parcelable.Creator<FavoriteTvshow> CREATOR = new Parcelable.Creator<FavoriteTvshow>() {
        @Override
        public FavoriteTvshow createFromParcel(Parcel source) {
            return new FavoriteTvshow(source);
        }

        @Override
        public FavoriteTvshow[] newArray(int size) {
            return new FavoriteTvshow[size];
        }
    };
}
