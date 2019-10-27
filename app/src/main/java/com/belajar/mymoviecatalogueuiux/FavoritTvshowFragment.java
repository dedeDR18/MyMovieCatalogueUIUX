package com.belajar.mymoviecatalogueuiux;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.belajar.mymoviecatalogueuiux.adapter.listFavMoviesAdapter;
import com.belajar.mymoviecatalogueuiux.adapter.listFavTvshowAdapter;
import com.belajar.mymoviecatalogueuiux.db.MovieFavHelper;
import com.belajar.mymoviecatalogueuiux.db.TvshowFavHelper;
import com.belajar.mymoviecatalogueuiux.helper.MappingHelper;
import com.belajar.mymoviecatalogueuiux.helper.MappingHelperTvshow;
import com.belajar.mymoviecatalogueuiux.model.FavoriteMovies;
import com.belajar.mymoviecatalogueuiux.model.FavoriteTvshow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritTvshowFragment extends Fragment implements LoadTvshowFavCallback{
    private RecyclerView rvFavTvshow;
    private listFavTvshowAdapter adapter;
    private TvshowFavHelper tvshowFavHelper;
    private ProgressBar progressBar;


    public FavoritTvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_favorit_tvshow, container, false);
        rvFavTvshow = v.findViewById(R.id.rvFavTvshow);
        progressBar = v.findViewById(R.id.pb_tvshow_fav);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvFavTvshow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTvshow.setHasFixedSize(true);
        adapter = new listFavTvshowAdapter(getActivity());
        rvFavTvshow.setAdapter(adapter);
        tvshowFavHelper = TvshowFavHelper.getInstance(getActivity().getApplicationContext());
        tvshowFavHelper.open();
        new FavoritTvshowFragment.LoadFavDataAsync(tvshowFavHelper, this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvshowFavHelper.close();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavoriteTvshow> favoriteTvshows) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favoriteTvshows.size() > 0){
            adapter.setListFavTvshow(favoriteTvshows);
        } else {
            adapter.setListFavTvshow(new ArrayList<FavoriteTvshow>());
        }
    }

    private static class LoadFavDataAsync extends AsyncTask<Void, Void, ArrayList<FavoriteTvshow>> {
        private final WeakReference<TvshowFavHelper> weakTvshowFavHelper;
        private final WeakReference<LoadTvshowFavCallback> weakCallback;

        private LoadFavDataAsync(TvshowFavHelper tvshowFavHelper, LoadTvshowFavCallback callback){
            weakTvshowFavHelper = new WeakReference<>(tvshowFavHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }


        @Override
        protected ArrayList<FavoriteTvshow> doInBackground(Void... voids) {
            Cursor dataCursor = weakTvshowFavHelper.get().queryAll();
            return MappingHelperTvshow.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<FavoriteTvshow> favoriteTvshows){
            super.onPostExecute(favoriteTvshows);

            weakCallback.get().postExecute(favoriteTvshows);
        }
    }

}
interface LoadTvshowFavCallback {
    void preExecute();
    void postExecute(ArrayList<FavoriteTvshow> favoriteTvshows);
}
