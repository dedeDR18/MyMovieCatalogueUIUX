package com.belajar.mymoviecatalogueuiux;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.belajar.mymoviecatalogueuiux.adapter.TabAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private TabLayout tabLayout;
    private TabItem tabTvshow, tabMovies;
    private ViewPager vPager;
    private TabAdapter tabAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        Log.d("tab", "fragment favorite");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = getView().findViewById(R.id.tab_layout_favorite);
        tabMovies = getView().findViewById(R.id.tab_movies);
        tabTvshow = getView().findViewById(R.id.tab_tvshows);
        vPager = getView().findViewById(R.id.view_pager);

        tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragmentTab(new FavoritMoviesFragment(), getString(R.string.title_Movies));
        tabAdapter.addFragmentTab(new FavoritTvshowFragment(), getString(R.string.title_tvshows));
        vPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(vPager);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.option_search);
        if(item!=null)
            item.setVisible(false);
    }
}
