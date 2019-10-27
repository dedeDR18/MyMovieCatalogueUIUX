package com.belajar.mymoviecatalogueuiux;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.belajar.mymoviecatalogueuiux.activity.TvshowDetailActivity;
import com.belajar.mymoviecatalogueuiux.adapter.ListTvshowAdapter;
import com.belajar.mymoviecatalogueuiux.model.Tvshow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {
    private ListTvshowAdapter adapter;
    private TvshowViewModel tvshowViewModel;
    private ProgressBar progressBar;
    private ArrayList<Tvshow> tvshows;
    private RecyclerView rvTvshow;


    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tvshow, container, false);
        adapter = new ListTvshowAdapter();
        adapter.notifyDataSetChanged();

        progressBar = v.findViewById(R.id.progressBartv);
        rvTvshow = v.findViewById(R.id.rv_tvshow);
        rvTvshow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvshow.setAdapter(adapter);

        tvshowViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvshowViewModel.getTvshow().observe(this, getTvshows);
        tvshowViewModel.setTvshow();

        showLoading(true);
        adapter.setOnItemClickCallback(new ListTvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tvshow data) {
                showSelectedTvshow(data);
            }
        });


        return v;
    }




    private Observer<ArrayList<Tvshow>> getTvshows = new Observer<ArrayList<Tvshow>>() {
        @Override
        public void onChanged(ArrayList<Tvshow> tvshowItems) {
            if (tvshowItems != null){
                adapter.setMdataTvshow(tvshowItems);
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



    private void showSelectedTvshow(Tvshow tvshow){
        Toast.makeText(getActivity(), getContext().getString(R.string.chosen)+" "+ tvshow.getName(), Toast.LENGTH_SHORT).show();

        Intent moveToDetailIntent = new Intent(getContext(), TvshowDetailActivity.class);
        moveToDetailIntent.putExtra(TvshowDetailActivity.EXTRA_TVSHOW, tvshow);
        startActivity(moveToDetailIntent);
    }

}
