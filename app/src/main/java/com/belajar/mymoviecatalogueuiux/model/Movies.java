package com.belajar.mymoviecatalogueuiux.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movies implements Parcelable {

    private int id;
    private String posterpath;
    private String title;
    private String overview;
    private String daterelease;
    private double userscore;
    private double popularity;

    public double getUserscore() {
        return userscore;
    }

    public void setUserscore(double userscore) {
        this.userscore = userscore;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(String daterelease) {
        this.daterelease = daterelease;
    }

    public Movies() {
    }

    public Movies(JSONObject object){
        try{
            int id = object.getInt("id");
            String posterpath = object.getString("poster_path");
            String title = object.getString("title");
            String daterelease = object.getString("release_date");
            String overview = object.getString("overview");
            double userscore = object.getDouble("vote_average");
            double popularity = object.getDouble("popularity");

            this.id = id;
            this.posterpath = posterpath;
            this.title = title;
            this.daterelease = daterelease;
            this.overview = overview;
            this.userscore = userscore;
            this.popularity = popularity;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterpath);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.daterelease);
        dest.writeDouble(this.userscore);
        dest.writeDouble(this.popularity);
    }

    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.posterpath = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.daterelease = in.readString();
        this.userscore = in.readDouble();
        this.popularity = in.readDouble();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
