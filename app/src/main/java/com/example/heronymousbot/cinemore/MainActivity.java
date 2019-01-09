package com.example.heronymousbot.cinemore;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heronymousbot.cinemore.AdaptersUtils.MovieAdapter;
import com.example.heronymousbot.cinemore.ClassesUtils.Films;
import com.example.heronymousbot.cinemore.NetworkUtils.MainNetworkUtils;
import com.example.heronymousbot.cinemore.NetworkUtils.MainQueryUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private TextView mWhenEmptyTextView;
    private ImageView noConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mWhenEmptyTextView = findViewById(R.id.when_empty_tv);
        mRecyclerView = findViewById(R.id.rv_movie_list);
        noConnection = findViewById(R.id.no_connection_image);
        makeMovieSearchQuery("popular");
    }

    private void makeMovieSearchQuery(String movieCategory) {

        URL movieSearchUrl = MainNetworkUtils.buildUrl(movieCategory);
        new MovieCategoryQueryTask().execute(movieSearchUrl);
    }

    public class MovieCategoryQueryTask extends AsyncTask<URL, Void, ArrayList<Films>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView.setVisibility(View.GONE);
            noConnection.setVisibility(View.GONE);
            mWhenEmptyTextView.setVisibility(View.GONE);
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Films> doInBackground(URL... params) {

            URL searchUrl = params[0];
            ArrayList<Films> moviesList = null;
            try {
                moviesList = MainQueryUtils.fetchMovieData(searchUrl.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return moviesList;
        }


        @Override
        protected void onPostExecute(ArrayList<Films> moviesList) {
            if (isOnline()) {
                mLoadingIndicator.setVisibility(View.GONE);
                mWhenEmptyTextView.setVisibility(View.GONE);
                noConnection.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new MovieAdapter(getApplicationContext(), moviesList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mRecyclerView.setVisibility(View.GONE);
                mLoadingIndicator.setVisibility(View.GONE);
                mWhenEmptyTextView.setVisibility(View.VISIBLE);
                noConnection.setVisibility(View.VISIBLE);
                mWhenEmptyTextView.setText(getString(R.string.no_connection));
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {

            case R.id.most_popular_option:
                if (isOnline()) {
                    Toast.makeText(this, "These are the most popular movies!",
                            Toast.LENGTH_SHORT).show();
                    makeMovieSearchQuery("popular");
                }

                return true;

            case R.id.now_playing_option:
                if (isOnline()) {
                    Toast.makeText(this, "These are playing now!",
                            Toast.LENGTH_SHORT).show();
                    makeMovieSearchQuery("now_playing");
                }

                return true;

            case R.id.top_rated_option:
                if (isOnline()) {
                    Toast.makeText(this, "These are the top rated movies!",
                            Toast.LENGTH_SHORT).show();
                    makeMovieSearchQuery("top_rated");
                }

                return true;

            case R.id.favorites_option:
                if (isOnline()) {
                    Toast.makeText(this, "These are your favorite movies!",
                            Toast.LENGTH_SHORT).show();
                    Intent FavoriteIntent = new Intent(getApplicationContext(), FavoriteMoviesActivity.class);
                    startActivity(FavoriteIntent);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }
}


