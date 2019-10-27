package com.belajar.mymoviecatalogueuiux.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.mymoviecatalogueuiux.model.Movies;
import com.belajar.mymoviecatalogueuiux.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListViewHolder> {

    private ArrayList<Movies> mdata = new ArrayList<>();


    private OnItemClickCallback onItemClickCallback;


    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setMdatamovie(ArrayList<Movies> items){
        mdata.clear();
        mdata.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        String className = parent.getContext().getClass().getSimpleName();

        if(className.equals("MainActivity")){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new ListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            return new ListViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onItemClickCallback.onItemClicked(mdata.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView posterMovies;
        TextView tvTitle, tvDateRelease;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            posterMovies = itemView.findViewById(R.id.poster_movie);
            tvTitle = itemView.findViewById(R.id.tv_title_movie);
            tvDateRelease = itemView.findViewById(R.id.tv_movie_date);
            posterMovies.setClipToOutline(true);
        }

        void bind(Movies movies){
            tvDateRelease.setText(movies.getDaterelease());
            tvTitle.setText(movies.getTitle());
            Log.d("image", "asd" + movies.getTitle());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"+movies.getPosterpath())
                    .placeholder(R.drawable.ic_movies)
                    .error(R.drawable.error)
                    .into(posterMovies);
        }
    }


    public interface OnItemClickCallback{
        void onItemClicked(Movies data);
    }


}
