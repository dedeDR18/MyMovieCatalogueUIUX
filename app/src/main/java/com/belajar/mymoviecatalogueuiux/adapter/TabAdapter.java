package com.belajar.mymoviecatalogueuiux.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.belajar.mymoviecatalogueuiux.FavoritMoviesFragment;
import com.belajar.mymoviecatalogueuiux.FavoritTvshowFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> myFragment = new ArrayList<>();
    private List<String> tabTitle = new ArrayList<>();

    private int numOftab;
    public TabAdapter (FragmentManager fm){
        super(fm);
    }

    public void addFragmentTab(Fragment fragment, String title){
        myFragment.add(fragment);
        tabTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return myFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitle.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
