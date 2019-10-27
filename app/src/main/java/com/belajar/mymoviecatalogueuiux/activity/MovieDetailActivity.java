package com.belajar.mymoviecatalogueuiux.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.belajar.mymoviecatalogueuiux.db.MovieFavHelper;
import com.belajar.mymoviecatalogueuiux.model.Movies;
import com.belajar.mymoviecatalogueuiux.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Locale;

import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.DATERELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIEIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIETITLE;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvDateRelease, tvOverview, tvUserScore, tvPopularity;
    ImageView ivMvPoster;
    Button btnFavorite;
    ProgressBar progressBarDM;
    Toolbar toolbarMovie;
    MovieFavHelper movieFavHelper;
    public static final String EXTRA_MOVIE = "extra_movie";
    private String curLanguage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        curLanguage = Locale.getDefault().getLanguage();

        toolbarMovie = findViewById(R.id.toolbarMovie);
        setSupportActionBar(toolbarMovie);

        if (getSupportActionBar() != null){
            if (curLanguage.equals("in")){
                getSupportActionBar().setTitle("DETAIL FILM");
            } else {
                getSupportActionBar().setTitle("MOVIE DETAILS");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        movieFavHelper = MovieFavHelper.getInstance(this);

        progressBarDM = findViewById(R.id.progressBarDM);

        ivMvPoster = findViewById(R.id.iv_detailMvPoster);

        tvTitle = findViewById(R.id.tv_detailTitleMovie);
        tvDateRelease = findViewById(R.id.tv_detailDateRelease);
        tvOverview = findViewById(R.id.tv_detailOverview);
        tvUserScore = findViewById(R.id.tv_detailUserScore);
        tvPopularity = findViewById(R.id.tv_detailPopularity);
        btnFavorite = findViewById(R.id.btn_favorite);
        final Movies movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_favorite){
                    ContentValues values = new ContentValues();
                    values.put(MOVIETITLE, movie.getTitle());
                    values.put(MOVIEIMAGE, movie.getPosterpath());
                    values.put(DATERELEASE, movie.getDaterelease());

                    movieFavHelper.open();
                    Cursor c = movieFavHelper.selectByTitle(movie.getTitle());
                    if (c != null) {
                        if (c.moveToFirst() == true){
                            Toast.makeText(MovieDetailActivity.this, getString(R.string.data_sudah_ada), Toast.LENGTH_SHORT).show();
                        } else {
                            movieFavHelper.insert(values);
                            Toast.makeText(MovieDetailActivity.this, getString(R.string.data_boleh_masuk), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });




        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+movie.getPosterpath())
                .placeholder(R.drawable.ic_movies)
                .error(R.drawable.error)
                .into(ivMvPoster);
        tvTitle.setText(movie.getTitle());
        tvDateRelease.setText(movie.getDaterelease());
        tvOverview.setText(movie.getOverview());
        String userscore = new DecimalFormat("##.##").format(movie.getUserscore());
        tvUserScore.setText(userscore + " %");
        String popularity = new DecimalFormat("###.###").format(movie.getPopularity());
        tvPopularity.setText(popularity);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieFavHelper.close();
    }
}
