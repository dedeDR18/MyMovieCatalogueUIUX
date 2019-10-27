package com.belajar.mymoviecatalogueuiux.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.mymoviecatalogueuiux.R;
import com.belajar.mymoviecatalogueuiux.model.Tvshow;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListTvshowAdapter extends RecyclerView.Adapter<ListTvshowAdapter.ListViewHolder> {
    private ArrayList<Tvshow> mdata = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setMdataTvshow(ArrayList<Tvshow> items){
        mdata.clear();
        mdata.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        String className = parent.getContext().getClass().getSimpleName();

        if (className.equals("MainActivity")){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
            return new ListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_fav, parent, false);
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
        ImageView posterTvshow;
        TextView tvNameTvshow, tvDateRelease;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            posterTvshow = itemView.findViewById(R.id.poster_tvshow);
            tvNameTvshow = itemView.findViewById(R.id.tv_title_tvshow);
            tvDateRelease = itemView.findViewById(R.id.tv_dateRelease_tvshow);
            posterTvshow.setClipToOutline(true);

        }

        void bind(Tvshow tvshow){
            tvNameTvshow.setText(tvshow.getName());
            tvDateRelease.setText(tvshow.getDaterelease());
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"+tvshow.getPosterpath())
                    .placeholder(R.drawable.ic_movies)
                    .error(R.drawable.error)
                    .into(posterTvshow);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Tvshow data);
    }
}
