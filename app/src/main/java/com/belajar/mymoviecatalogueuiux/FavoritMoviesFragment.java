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
import com.belajar.mymoviecatalogueuiux.db.MovieFavHelper;
import com.belajar.mymoviecatalogueuiux.helper.MappingHelper;
import com.belajar.mymoviecatalogueuiux.model.FavoriteMovies;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritMoviesFragment extends Fragment implements LoadMovieFavCallback {
    private RecyclerView rvFavMovies;
    private listFavMoviesAdapter adapter;
    private MovieFavHelper movieFavHelper;
    private ProgressBar progressBar;


    public FavoritMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorit_movies, container, false);
        rvFavMovies = v.findViewById(R.id.rvFavMovies);
        progressBar = v.findViewById(R.id.pb_movie_fav);

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvFavMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovies.setHasFixedSize(true);
        adapter = new listFavMoviesAdapter(getActivity());
        rvFavMovies.setAdapter(adapter);
        movieFavHelper = MovieFavHelper.getInstance(getActivity().getApplicationContext());
        movieFavHelper.open();
        new LoadFavDataAsync(movieFavHelper, this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieFavHelper.close();
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
    public void postExecute(ArrayList<FavoriteMovies> favoriteMovies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favoriteMovies.size() > 0){
            adapter.setListFavMovies(favoriteMovies);
        } else {
            adapter.setListFavMovies(new ArrayList<FavoriteMovies>());
            Log.d("test", "tidak ada data");
        }
    }

    private static class LoadFavDataAsync extends AsyncTask<Void, Void, ArrayList<FavoriteMovies>>{
        private final WeakReference<MovieFavHelper> weakMovieFavHelper;
        private final WeakReference<LoadMovieFavCallback> weakCallback;

        private LoadFavDataAsync(MovieFavHelper movieFavHelper, LoadMovieFavCallback callback){
            weakMovieFavHelper = new WeakReference<>(movieFavHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }


        @Override
        protected ArrayList<FavoriteMovies> doInBackground(Void... voids) {
            Cursor dataCursor = weakMovieFavHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<FavoriteMovies> favoriteMovies){
            super.onPostExecute(favoriteMovies);

            weakCallback.get().postExecute(favoriteMovies);
        }
    }
}

interface LoadMovieFavCallback {
    void preExecute();
    void postExecute(ArrayList<FavoriteMovies> favoriteMovies);
}
