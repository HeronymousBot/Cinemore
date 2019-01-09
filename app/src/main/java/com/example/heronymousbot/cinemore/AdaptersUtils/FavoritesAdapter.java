package com.example.heronymousbot.cinemore.AdaptersUtils;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

import com.example.heronymousbot.cinemore.ClassesUtils.Films;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;
import com.example.heronymousbot.cinemore.FavoriteMovieViewModel;
import com.example.heronymousbot.cinemore.MovieDetail;
import com.example.heronymousbot.cinemore.R;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    public static final String KEY_TITLE = "title";
    public static final String KEY_POSTER = "poster";
    public static final String KEY_RELEASE_DATE = "releaseDate";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_VOTE_AVERAGE = "voteAverage";
    public static final String KEY_MOVIE_ID = "movie_id";

    private List<FavoriteMovie> moviesList;
    private Context context;

    public FavoritesAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new FavoritesViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, final int position) {
        final FavoriteMovie currentMovie = moviesList.get(position);
        Picasso.get()
                .load(currentMovie.getMoviePoster()).fit().centerCrop().into(holder.moviePoster);

        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteMovie currentMovie = moviesList.get(position);
                Intent detailIntent = new Intent(v.getContext(), MovieDetail.class);
                detailIntent.putExtra(KEY_TITLE, currentMovie.getMovieTitle());
                detailIntent.putExtra(KEY_POSTER, currentMovie.getMoviePoster());
                detailIntent.putExtra(KEY_RELEASE_DATE, currentMovie.getReleaseDate());
                detailIntent.putExtra(KEY_OVERVIEW, currentMovie.getOverview());
                detailIntent.putExtra(KEY_VOTE_AVERAGE, currentMovie.getAverageVote());
                detailIntent.putExtra(KEY_MOVIE_ID, currentMovie.getMovieID());
                v.getContext().startActivity(detailIntent);
            }
        });


    }

    public void setMovies(List<FavoriteMovie> favoriteMovies) {
        this.moviesList = favoriteMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public int getItemCount() {
        return moviesList != null ? moviesList.size() : 0;
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;


        public FavoritesViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.poster_main);

        }
    }
}
