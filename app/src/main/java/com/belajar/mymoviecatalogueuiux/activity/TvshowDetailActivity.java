package com.belajar.mymoviecatalogueuiux.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.belajar.mymoviecatalogueuiux.R;
import com.belajar.mymoviecatalogueuiux.db.TvshowFavHelper;
import com.belajar.mymoviecatalogueuiux.model.Tvshow;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Locale;

import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.DATERELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIEIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.MovieColumns.MOVIETITLE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWIMAGE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWRELEASE;
import static com.belajar.mymoviecatalogueuiux.db.DatabaseContract.TvshowColumns.TVSHOWTITLE;

public class TvshowDetailActivity extends AppCompatActivity {
    TextView tvNameTVshow, tvDateReleaseTVshow, tvUserScoreTvshow, tvLanguageTvshow, tvPopularityTvshow, tvOverviewTvshow;
    ImageView ivPosterTvshow;
    Toolbar toolbarTv;
    Button btnFavoriteTS;
    private String curLanguage = "";
    TvshowFavHelper tvshowFavHelper;

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        curLanguage = Locale.getDefault().getLanguage();

        toolbarTv = findViewById(R.id.toolbarTvshow);
        setSupportActionBar(toolbarTv);
        if (getSupportActionBar() != null){
            if (curLanguage.equals("in")){
                getSupportActionBar().setTitle("DETAIL ACARA TV");
            } else {
                getSupportActionBar().setTitle("TV SHOW DETAILS");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvNameTVshow = findViewById(R.id.tv_detailTitleTvshow);
        tvDateReleaseTVshow = findViewById(R.id.tv_detailDateReleaseTvshow);
        tvUserScoreTvshow = findViewById(R.id.tv_detailUserScoreTvshow);
        tvLanguageTvshow = findViewById(R.id.tv_detailLanguage);
        tvPopularityTvshow = findViewById(R.id.tv_detailpopularitytvshow);
        tvOverviewTvshow = findViewById(R.id.tv_detailOverview);
        ivPosterTvshow = findViewById(R.id.iv_detailTvshowPoster);
        final Tvshow tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        tvshowFavHelper = TvshowFavHelper.getInstance(this);
        btnFavoriteTS = findViewById(R.id.btn_favorite_tvshow);
        btnFavoriteTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_favorite_tvshow){
                    ContentValues values = new ContentValues();
                    values.put(TVSHOWTITLE, tvshow.getName());
                    values.put(TVSHOWIMAGE, tvshow.getPosterpath());
                    values.put(TVSHOWRELEASE, tvshow.getDaterelease());

                    tvshowFavHelper.open();
                    Cursor c = tvshowFavHelper.selectByTitle(tvshow.getName());
                    if (c != null) {
                        if (c.moveToFirst() == true){
                            Toast.makeText(TvshowDetailActivity.this, getString(R.string.data_sudah_ada), Toast.LENGTH_SHORT).show();
                        } else {
                            tvshowFavHelper.insert(values);
                            Toast.makeText(TvshowDetailActivity.this, getString(R.string.data_boleh_masuk), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });


        String userscore = new DecimalFormat("##.##").format(tvshow.getUserscore());
        String popularity = new DecimalFormat("##.##").format(tvshow.getPopularity());
        String language = tvshow.getLanguage();
        if (language.equals("en")){
            tvLanguageTvshow.setText("English");
        }else {
            tvLanguageTvshow.setText("Japanese");
        }

        tvNameTVshow.setText(tvshow.getName());
        tvDateReleaseTVshow.setText(tvshow.getDaterelease());
        tvUserScoreTvshow.setText(userscore + " %");
        tvPopularityTvshow.setText(popularity);
        tvOverviewTvshow.setText(tvshow.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+tvshow.getPosterpath())
                .placeholder(R.drawable.ic_movies)
                .error(R.drawable.error)
                .into(ivPosterTvshow);
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
        tvshowFavHelper.close();
    }
}
