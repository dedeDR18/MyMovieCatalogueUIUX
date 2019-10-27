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
import com.belajar.mymoviecatalogueuiux.model.FavoriteMovies;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class listFavMoviesAdapter extends RecyclerView.Adapter<listFavMoviesAdapter.favMoviesViewHolder> {
    private MovieFavHelper movieFavHelper;
    private ArrayList<FavoriteMovies> listFavMovies = new ArrayList<>();
    private Activity activity;


    public listFavMoviesAdapter(Activity activity){
        this.activity=activity;
    }

    public ArrayList<FavoriteMovies> getListFavMovies(){
        return listFavMovies;
    }

    public void setListFavMovies(ArrayList<FavoriteMovies> listFavMovies){
        if (listFavMovies.size() > 0){
            this.listFavMovies.clear();
        }
        this.listFavMovies.addAll(listFavMovies);

        notifyDataSetChanged();
    }


    public void removeItem(int position){
        this.listFavMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavMovies.size());
    }
    @NonNull
    @Override
    public favMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_fav, parent, false);
        return new favMoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final favMoviesViewHolder holder, int position) {
        holder.tvMovieTitle.setText(listFavMovies.get(position).getMovieTitle());
        holder.tvMovieDate.setText(listFavMovies.get(position).getMovieDateRelease());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+listFavMovies.get(position).getMovieImage())
                .placeholder(R.drawable.ic_movies)
                .error(R.drawable.error)
                .into(holder.ivMovieFav);
        holder.cvMovieFav.setOnClickListener( new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, final int position) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

                alertDialogBuilder.setTitle(R.string.dialog_movie_title);
                alertDialogBuilder
                        .setMessage(R.string.dialog_movie_massage)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                movieFavHelper = MovieFavHelper.getInstance(activity);
                                movieFavHelper.open();
                                movieFavHelper.deleteById(String.valueOf(listFavMovies.get(position).getId()));
                                movieFavHelper.close();
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
        return listFavMovies.size();
    }

    public class favMoviesViewHolder extends RecyclerView.ViewHolder {
        final TextView tvMovieTitle, tvMovieDate;
        final ImageView ivMovieFav;
        final CardView cvMovieFav;

        public favMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_favTitle);
            tvMovieDate = itemView.findViewById(R.id.tv_movie_favDate);
            ivMovieFav = itemView.findViewById(R.id.image_movie_fav);
            cvMovieFav = itemView.findViewById(R.id.cv_item_movieFav);
        }
    }
}
