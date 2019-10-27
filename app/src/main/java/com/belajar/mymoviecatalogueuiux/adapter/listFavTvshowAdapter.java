package com.belajar.mymoviecatalogueuiux.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.mymoviecatalogueuiux.CustomOnItemClickListener;
import com.belajar.mymoviecatalogueuiux.R;
import com.belajar.mymoviecatalogueuiux.db.MovieFavHelper;
import com.belajar.mymoviecatalogueuiux.db.TvshowFavHelper;
import com.belajar.mymoviecatalogueuiux.model.FavoriteMovies;
import com.belajar.mymoviecatalogueuiux.model.FavoriteTvshow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class listFavTvshowAdapter extends RecyclerView.Adapter<listFavTvshowAdapter.favTvshowViewHolder> {
    private TvshowFavHelper tvshowFavHelper;
    private ArrayList<FavoriteTvshow> listFavTvshow = new ArrayList<>();
    private Activity activity;


    public listFavTvshowAdapter(Activity activity){
        this.activity=activity;
    }

    public ArrayList<FavoriteTvshow> getListFavTvshow(){
        return listFavTvshow;
    }

    public void setListFavTvshow(ArrayList<FavoriteTvshow> listFavTvshow){
        if (listFavTvshow.size() > 0){
            this.listFavTvshow.clear();
        }
        this.listFavTvshow.addAll(listFavTvshow);

        notifyDataSetChanged();
    }


    public void removeItem(int position){
        this.listFavTvshow.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavTvshow.size());
    }
    @NonNull
    @Override
    public favTvshowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_fav, parent, false);
        return new listFavTvshowAdapter.favTvshowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull favTvshowViewHolder holder, int position) {
        holder.tvTvshowTitle.setText(listFavTvshow.get(position).getTvshowTitle());
        holder.tvTvshowDate.setText(listFavTvshow.get(position).getTvshowRelease());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+listFavTvshow.get(position).getTvshowImage())
                .placeholder(R.drawable.ic_movies)
                .error(R.drawable.error)
                .into(holder.ivTvshowFav);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

                alertDialogBuilder.setTitle(R.string.dialog_tvshow_title);
                alertDialogBuilder
                        .setMessage(R.string.dialog_tvshow_massage)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               tvshowFavHelper = TvshowFavHelper.getInstance(activity);
                                tvshowFavHelper.open();
                                tvshowFavHelper.deleteById(String.valueOf(listFavTvshow.get(position).getId()));
                                tvshowFavHelper.close();
                                removeItem(position);




                            }
                        })
                        .setNegativeButton(R.string.tidak, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listFavTvshow.size();
    }

    public class favTvshowViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTvshowTitle, tvTvshowDate;
        final ImageView ivTvshowFav;
        final CardView cvTvshowFav;
        public favTvshowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTvshowTitle = itemView.findViewById(R.id.tv_title_tvshow);
            tvTvshowDate = itemView.findViewById(R.id.tv_dateRelease_tvshow);
            ivTvshowFav = itemView.findViewById(R.id.poster_tvshow);
            cvTvshowFav = itemView.findViewById(R.id.cv_item_tvshowFav);
        }
    }
}
