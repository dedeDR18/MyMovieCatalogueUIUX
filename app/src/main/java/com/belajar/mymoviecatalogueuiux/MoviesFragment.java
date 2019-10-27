package com.belajar.mymoviecatalogueuiux;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.belajar.mymoviecatalogueuiux.activity.MovieDetailActivity;
import com.belajar.mymoviecatalogueuiux.adapter.ListMoviesAdapter;
import com.belajar.mymoviecatalogueuiux.model.Movies;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private TypedArray dataPoster;
    private ArrayList<Movies> movies;
    RecyclerView rvMovies;
    private ListMoviesAdapter adapter;
    private MovieViewModel movieViewModel;
    private ProgressBar progressBar;


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        adapter = new ListMoviesAdapter();
        adapter.notifyDataSetChanged();

        progressBar = v.findViewById(R.id.progressBar);

        rvMovies = v.findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvMovies.setAdapter(adapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);
        movieViewModel.setMovie();

        showLoading(true);
        adapter.setOnItemClickCallback(new ListMoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movies data) {
                showSelectedMovie(data);
            }
        });
        return v;
    }

    private Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movieItems) {
            if (movieItems != null){
                adapter.setMdatamovie(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie(Movies movies){
        Toast.makeText(getActivity(), getContext().getString(R.string.chosen)+" "+ movies.getTitle(), Toast.LENGTH_SHORT).show();

        Intent moveToDetailIntent = new Intent(getContext(), MovieDetailActivity.class);
        moveToDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies);
        startActivity(moveToDetailIntent);
    }

}
