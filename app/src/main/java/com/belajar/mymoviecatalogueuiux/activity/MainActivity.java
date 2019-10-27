package com.belajar.mymoviecatalogueuiux.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.belajar.mymoviecatalogueuiux.FavoriteFragment;
import com.belajar.mymoviecatalogueuiux.MoviesFragment;
import com.belajar.mymoviecatalogueuiux.R;
import com.belajar.mymoviecatalogueuiux.TvshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private final String STATE_TITLE = "state_title";
    private final String STATE_MODE = "state_mode";
    private final String SEARCH_MOVIE = "search_movie";
    private final String SEARCH_TVSHOW = "search_tvshow";
    private int mode;
    Fragment mFragment;

    private String actionbarTitle="";
    private Toolbar toolbar;
    private String curLanguage = "";

    private SearchManager searchManager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment mFragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    if (curLanguage.equals("in")) {
                        actionbarTitle = "FILM";
                    } else {
                        actionbarTitle = "MOVIES";
                    }
                    setActionBarTitle(actionbarTitle);
                    mFragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, mFragment, mFragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tvshow:
                    if (curLanguage.equals("in")) {
                        actionbarTitle = "ACARA TV";
                    } else {
                        actionbarTitle = "TV SHOWS";
                    }
                    setActionBarTitle(actionbarTitle);
                    mFragment = new TvshowFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, mFragment, mFragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorite:
                    if (curLanguage.equals("in")) {
                        actionbarTitle = "FAVORIT";
                    } else {
                        actionbarTitle = "FAVORITE";
                    }
                    setActionBarTitle(actionbarTitle);
                    mFragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, mFragment, mFragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curLanguage = Locale.getDefault().getLanguage();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){

            navView.setSelectedItemId(R.id.navigation_movie);
        } else {

            actionbarTitle = savedInstanceState.getString(STATE_TITLE);
            mode = savedInstanceState.getInt(STATE_MODE);
            bahasaActionTitlebar();
            setActionBarTitle(actionbarTitle);
            setMode(mode);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if ( searchManager != null ){
            SearchView searchView = (SearchView)(menu.findItem(R.id.option_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint("Search");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.container_layout);
                    if (fragmentInFrame instanceof MoviesFragment){
                        Log.d("debug", "you are in Movies fragment");
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_QUERY, query);
                        intent.putExtra(SearchActivity.EXTRA_TYPE_SEARCH, SEARCH_MOVIE);
                        startActivity(intent);
                    } else if (fragmentInFrame instanceof TvshowFragment) {
                        Log.d("debug", "you are in tvshow fragment");
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_QUERY, query);
                        intent.putExtra(SearchActivity.EXTRA_TYPE_SEARCH, SEARCH_TVSHOW);
                        startActivity(intent);
                    }
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.option_menu){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, actionbarTitle);
        outState.putInt(STATE_MODE, mode);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode){
            case R.id.navigation_movie:
                if (curLanguage.equals("in")) {
                    actionbarTitle = "FILM";
                } else {
                    actionbarTitle = "MOVIES";
                }
                setActionBarTitle(actionbarTitle);
                mFragment = new MoviesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, mFragment, mFragment.getClass().getSimpleName()).commit();
                break;
            case R.id.navigation_tvshow:
                if (curLanguage.equals("in")) {
                    actionbarTitle = "ACARA TV";
                } else {
                    actionbarTitle = "TV SHOWS";
                }
                setActionBarTitle(actionbarTitle);
                mFragment = new TvshowFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, mFragment, mFragment.getClass().getSimpleName()).commit();
                break;
        }
        mode = selectedMode;

    }

    private void setActionBarTitle( String actionbarTitle){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionbarTitle);
        }
    }

    private void bahasaActionTitlebar(){
        curLanguage = Locale.getDefault().getLanguage();

        if(curLanguage.equals("in") && actionbarTitle.equals("MOVIES")){
            actionbarTitle = "FILM";
            setActionBarTitle(actionbarTitle);
        }

        if(curLanguage.equals("in") && actionbarTitle.equals("TV SHOWS")){
            actionbarTitle = "ACARA TV";
            setActionBarTitle(actionbarTitle);
        }

        if(curLanguage.equals("in") && actionbarTitle.equals("FAVORITE")){
            actionbarTitle = "FAVORIT";
            setActionBarTitle(actionbarTitle);
        }

        if(curLanguage.equals("en") && actionbarTitle.equals("ACARA TV")){
            actionbarTitle = "TV SHOWS";
            setActionBarTitle(actionbarTitle);
        }

        if(curLanguage.equals("en") && actionbarTitle.equals("FILM")){
            actionbarTitle = "MOVIES";
            setActionBarTitle(actionbarTitle);
        }

        if(curLanguage.equals("en") && actionbarTitle.equals("FAVORIT")){
            actionbarTitle = "FAVORITE";
            setActionBarTitle(actionbarTitle);
        }

    }


}
