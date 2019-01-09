package com.example.heronymousbot.cinemore;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heronymousbot.cinemore.AdaptersUtils.MovieAdapter;
import com.example.heronymousbot.cinemore.AdaptersUtils.ReviewAdapter;
import com.example.heronymousbot.cinemore.AdaptersUtils.TrailerAdapter;
import com.example.heronymousbot.cinemore.ClassesUtils.Films;
import com.example.heronymousbot.cinemore.ClassesUtils.Reviews;
import com.example.heronymousbot.cinemore.ClassesUtils.Trailers;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;
import com.example.heronymousbot.cinemore.NetworkUtils.MainNetworkUtils;
import com.example.heronymousbot.cinemore.NetworkUtils.MainQueryUtils;
import com.example.heronymousbot.cinemore.NetworkUtils.ReviewQueryUtils;
import com.example.heronymousbot.cinemore.NetworkUtils.SpecificNetworkUtils;
import com.example.heronymousbot.cinemore.NetworkUtils.TrailersQueryUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {
    private TextView titleTextView;
    private LikeButton heartButton;
    private TextView averageVoteTextView;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private ImageView moviePosterImageView;
    private RecyclerView mReviewsRecyclerView;
    private RecyclerView mTrailersRecyclerView;
    private TextView mTrailerTextView;
    private TextView mReviewTextView;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private FavoriteMovieViewModel mFavoriteMovieViewModel;
    FavoriteMovie mFavoriteMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = findViewById(R.id.movie_title_detail);
        averageVoteTextView = findViewById(R.id.movie_vote_average_detail);
        overviewTextView = findViewById(R.id.movie_overview_detail);
        moviePosterImageView = findViewById(R.id.poster_detail);
        releaseDateTextView = findViewById(R.id.movie_release_date_detail);
        heartButton = findViewById(R.id.heart_button);
        mReviewsRecyclerView = findViewById(R.id.rv_review_list);
        mTrailersRecyclerView = findViewById(R.id.rv_trailer_list);
        mTrailerTextView = findViewById(R.id.trailer_tv);
        mReviewTextView = findViewById(R.id.review_tv);

        Intent intent = getIntent();

        String movieTitle = intent.getStringExtra(MovieAdapter.KEY_TITLE);
        String averageVote = intent.getStringExtra(MovieAdapter.KEY_VOTE_AVERAGE);
        String movieOverview = intent.getStringExtra(MovieAdapter.KEY_OVERVIEW);
        String releaseDate = intent.getStringExtra(MovieAdapter.KEY_RELEASE_DATE);
        String movieId = intent.getStringExtra(MovieAdapter.KEY_MOVIE_ID);
        String moviePoster = intent.getStringExtra(MovieAdapter.KEY_POSTER);

        FavoriteMovie favoriteMovie = new FavoriteMovie(movieTitle, movieId, releaseDate, moviePoster, movieOverview, averageVote);
        mFavoriteMovieViewModel = ViewModelProviders.of(MovieDetail.this).get(FavoriteMovieViewModel.class);

        if (mFavoriteMovieViewModel.foundById(favoriteMovie.getMovieID())) {
            heartButton.setLiked(true);
        }

        String modifiedDate;
        if (releaseDate != null) {
            modifiedDate = releaseDate.substring(8, 10) + "/" +
                    releaseDate.substring(5, 7) + "/" + releaseDate.substring(0, 4);

            modifiedDate = "Released in " + modifiedDate;
        } else {
            modifiedDate = "No release date available.";
        }


        titleTextView.setText(movieTitle);
        overviewTextView.setText(movieOverview);
        releaseDateTextView.setText(modifiedDate);
        averageVoteTextView.setText(averageVote);
        Picasso.get().load(moviePoster).fit().into(moviePosterImageView);
        makeMovieReviewSearchQuery(movieId);
        makeMovieTrailerSearchQuery(movieId);


        heartButton.setOnLikeListener(new OnLikeListener() {

            @Override
            public void liked(LikeButton likeButton) {

                try {

                    mFavoriteMovieViewModel.insert(favoriteMovie);
                    Toast.makeText(MovieDetail.this, "You added \"" + movieTitle + "\" to your favorites!", Toast.LENGTH_SHORT).show();


                } catch (NullPointerException e) {
                    Toast.makeText(MovieDetail.this, "Error inserting movie to database.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void unLiked(LikeButton likeButton) {

                try {
                    mFavoriteMovieViewModel.deleteById(movieId);
                    Toast.makeText(MovieDetail.this, "You deleted \"" + movieTitle + "\" from your favorites.", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Toast.makeText(MovieDetail.this, "No way to delete this from database", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void makeMovieReviewSearchQuery(String movieId) {

        URL reviewSearchUrl = SpecificNetworkUtils.buildReviewsUrl(movieId);
        new ReviewsQueryTask().execute(reviewSearchUrl);
    }

    private void makeMovieTrailerSearchQuery(String movieId) {

        URL trailersSearchUrl = SpecificNetworkUtils.buildTrailersUrl(movieId);
        new TrailersQueryTask().execute(trailersSearchUrl);
    }


    public class ReviewsQueryTask extends AsyncTask<URL, Void, ArrayList<Reviews>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected ArrayList<Reviews> doInBackground(URL... params) {

            URL searchUrl = params[0];
            ArrayList<Reviews> reviewsList = null;
            try {
                reviewsList = ReviewQueryUtils.fetchReviewData(searchUrl.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return reviewsList;
        }


        @Override
        protected void onPostExecute(ArrayList<Reviews> reviewsList) {
            if (isOnline()) {

                mReviewsRecyclerView.setVisibility(View.VISIBLE);
                mReviewsRecyclerView.setHasFixedSize(true);
                mReviewAdapter = new ReviewAdapter(getApplicationContext(), reviewsList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mReviewsRecyclerView.setLayoutManager(layoutManager);
                mReviewsRecyclerView.setAdapter(mReviewAdapter);
                mReviewsRecyclerView.setNestedScrollingEnabled(false);
            } else {
                mReviewTextView.setVisibility(View.GONE);
                mReviewsRecyclerView.setVisibility(View.GONE);

            }

        }

    }


    public class TrailersQueryTask extends AsyncTask<URL, Void, ArrayList<Trailers>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected ArrayList<Trailers> doInBackground(URL... params) {

            URL searchUrl = params[0];
            ArrayList<Trailers> trailersList = null;
            try {
                trailersList = TrailersQueryUtils.fetchTrailerData(searchUrl.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return trailersList;
        }


        @Override
        protected void onPostExecute(ArrayList<Trailers> trailersList) {
            if (isOnline()) {

                mTrailersRecyclerView.setVisibility(View.VISIBLE);
                mTrailersRecyclerView.setHasFixedSize(true);
                mTrailerAdapter = new TrailerAdapter(getApplicationContext(), trailersList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                mTrailersRecyclerView.setLayoutManager(layoutManager);
                mTrailersRecyclerView.setAdapter(mTrailerAdapter);
                mTrailersRecyclerView.setNestedScrollingEnabled(false);
            } else {
                mTrailerTextView.setVisibility(View.GONE);
                mTrailersRecyclerView.setVisibility(View.GONE);

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {

            case R.id.back_to_main:
                if (isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                return true;

            case R.id.go_to_favorites:
                if (isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), FavoriteMoviesActivity.class);
                    startActivity(intent);

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
