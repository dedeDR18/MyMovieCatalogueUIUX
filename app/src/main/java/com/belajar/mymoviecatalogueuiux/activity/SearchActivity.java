package com.belajar.mymoviecatalogueuiux.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.belajar.mymoviecatalogueuiux.MovieViewModel;
import com.belajar.mymoviecatalogueuiux.R;
import com.belajar.mymoviecatalogueuiux.TvshowViewModel;
import com.belajar.mymoviecatalogueuiux.adapter.ListMoviesAdapter;
import com.belajar.mymoviecatalogueuiux.adapter.ListTvshowAdapter;
import com.belajar.mymoviecatalogueuiux.model.Movies;
import com.belajar.mymoviecatalogueuiux.model.Tvshow;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_TYPE_SEARCH = "type_search";
    private Toolbar toolbarSearch;
    private RecyclerView rvSearch;
    private ListMoviesAdapter adapterMovie;
    private ListTvshowAdapter adapterTvshow;
    private MovieViewModel movieViewModel;
    private TvshowViewModel tvshowViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbarSearch = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbarSearch);
        rvSearch = findViewById(R.id.rv_search);
        String query = getIntent().getStringExtra(EXTRA_QUERY);
        String typeSearch = getIntent().getStringExtra(EXTRA_TYPE_SEARCH);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(query);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(typeSearch.equals("search_movie")){
            searchMovie(query);
            Toast.makeText(SearchActivity.this, "SEARCH MOVIE", Toast.LENGTH_SHORT).show();
        } else{
            searchTvshow(query);
            Toast.makeText(SearchActivity.this, "SEARCH TVSHOW", Toast.LENGTH_SHORT).show();
        }

    }

    private Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null){
                adapterMovie.setMdatamovie(movies);
            }
        }
    };

    private Observer<ArrayList<Tvshow>> getTvshow = new Observer<ArrayList<Tvshow>>() {
        @Override
        public void onChanged(ArrayList<Tvshow> tvshows) {
            if (tvshows != null){
                adapterTvshow.setMdataTvshow(tvshows);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchMovie(String query){
        adapterMovie = new ListMoviesAdapter();
        adapterMovie.notifyDataSetChanged();
        rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        rvSearch.setAdapter(adapterMovie);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMoviesQuery().observe(this, getMovie);
        movieViewModel.queryMovie(query);
        adapterMovie.setOnItemClickCallback(new ListMoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movies data) {
                showSelectedMovie(data);
            }
        });
    }

    public void searchTvshow(String query){
        adapterTvshow = new ListTvshowAdapter();
        adapterTvshow.notifyDataSetChanged();
        rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        rvSearch.setAdapter(adapterTvshow);
        tvshowViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvshowViewModel.getTvshowQuery().observe(this, getTvshow);
        tvshowViewModel.queryTvshow(query);
        adapterTvshow.setOnItemClickCallback(new ListTvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tvshow data) {
                showSelectedTvshow(data);
            }
        });
    }

    private void showSelectedTvshow(Tvshow tvshow){
        Toast.makeText(SearchActivity.this, getString(R.string.chosen)+" "+ tvshow.getName(), Toast.LENGTH_SHORT).show();

        Intent moveToDetailIntent = new Intent(SearchActivity.this, TvshowDetailActivity.class);
        moveToDetailIntent.putExtra(TvshowDetailActivity.EXTRA_TVSHOW, tvshow);
        startActivity(moveToDetailIntent);
    }

    private void showSelectedMovie(Movies movies){
        Toast.makeText(SearchActivity.this, getString(R.string.chosen)+" "+ movies.getTitle(), Toast.LENGTH_SHORT).show();

        Intent moveToDetailIntent = new Intent(SearchActivity.this, MovieDetailActivity.class);
        moveToDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies);
        startActivity(moveToDetailIntent);
    }
}
