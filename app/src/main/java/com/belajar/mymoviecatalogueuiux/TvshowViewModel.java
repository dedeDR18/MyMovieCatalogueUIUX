package com.belajar.mymoviecatalogueuiux;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.belajar.mymoviecatalogueuiux.model.Movies;
import com.belajar.mymoviecatalogueuiux.model.Tvshow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {
    private static String API_KEY = "883daeb3f5b3a32a160cc26536e7ce69";
    private MutableLiveData<ArrayList<Tvshow>> listTvshow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Tvshow>> listTvshowQuery = new MutableLiveData<>();

    void setTvshow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tvshow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvshow = list.getJSONObject(i);
                        Tvshow tvshowItems = new Tvshow(tvshow);
                        listItems.add(tvshowItems);
                    }
                    listTvshow.postValue(listItems);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Tvshow>> getTvshow(){
        return listTvshow;
    }

    public void queryTvshow(String query){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tvshow> listItemsQuery = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvshow = list.getJSONObject(i);
                        Tvshow tvshowItems = new Tvshow(tvshow);
                        listItemsQuery.add(tvshowItems);
                    }
                    listTvshowQuery.postValue(listItemsQuery);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Tvshow>> getTvshowQuery(){
        return listTvshowQuery;
    }
}
