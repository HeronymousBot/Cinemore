package com.example.heronymousbot.cinemore;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.heronymousbot.cinemore.AdaptersUtils.FavoritesAdapter;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;

import java.util.List;


public class FavoriteMoviesActivity extends AppCompatActivity {
    private ProgressBar mLoadingIndicator;
    private RecyclerView mFavoritesRecyclerView;
    private TextView mWhenEmptyTextView;
    private FavoriteMovieViewModel mFavoriteMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        mLoadingIndicator = findViewById(R.id.favorites_loading_indicator);
        mWhenEmptyTextView = findViewById(R.id.when__favorites_empty_tv);
        mLoadingIndicator.setVisibility(View.GONE);
        mFavoritesRecyclerView = findViewById(R.id.rv_favorites_list);
        mFavoritesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mFavoritesRecyclerView.setHasFixedSize(true);

        final FavoritesAdapter adapter = new FavoritesAdapter(this);
        mFavoritesRecyclerView.setAdapter(adapter);

        mFavoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        mFavoriteMovieViewModel.getAllFavoriteMovies().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                mLoadingIndicator.setVisibility(View.GONE);
                mWhenEmptyTextView.setVisibility(View.GONE);
                adapter.setMovies(favoriteMovies);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {

            case R.id.go_to_main:

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


                return true;

            case R.id.delete_all_favorites:
                mFavoriteMovieViewModel.deleteAllFavoriteMovies();


                return true;


            default:
                return super.onOptionsItemSelected(item);

        }


    }


}


