package com.belajar.mymoviecatalogueuiux.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Tvshow implements Parcelable {
    private int id;
    private String posterpath;
    private String name;
    private String daterelease;
    private double userscore;
    private String language;
    private double popularity;
    private String overview;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getUserscore() {
        return userscore;
    }

    public void setUserscore(double userscore) {
        this.userscore = userscore;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(String daterelease) {
        this.daterelease = daterelease;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public Tvshow() {
    }

    public Tvshow(JSONObject object){
        try{
            int id = object.getInt("id");
            String posterpath = object.getString("poster_path");
            String name = object.getString("name");
            String daterelease = object.getString("first_air_date");
            String overview = object.getString("overview");
            double popularity = object.getDouble("popularity");
            double userscore = object.getDouble("vote_average");
            String language = object.getString("original_language");

            this.id = id;
            this.posterpath = posterpath;
            this.name = name;
            this.daterelease = daterelease;
            this.overview = overview;
            this.popularity = popularity;
            this.userscore = userscore;
            this.language = language;
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
        dest.writeString(this.name);
        dest.writeString(this.daterelease);
        dest.writeDouble(this.userscore);
        dest.writeString(this.language);
        dest.writeDouble(this.popularity);
        dest.writeString(this.overview);
    }

    protected Tvshow(Parcel in) {
        this.id = in.readInt();
        this.posterpath = in.readString();
        this.name = in.readString();
        this.daterelease = in.readString();
        this.userscore = in.readDouble();
        this.language = in.readString();
        this.popularity = in.readDouble();
        this.overview = in.readString();
    }

    public static final Creator<Tvshow> CREATOR = new Creator<Tvshow>() {
        @Override
        public Tvshow createFromParcel(Parcel source) {
            return new Tvshow(source);
        }

        @Override
        public Tvshow[] newArray(int size) {
            return new Tvshow[size];
        }
    };
}
