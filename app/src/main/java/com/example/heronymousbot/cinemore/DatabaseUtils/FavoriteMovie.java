package com.example.heronymousbot.cinemore.DatabaseUtils;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_movies_table")
public class FavoriteMovie {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String movieTitle;
    private String movieID;
    private String releaseDate;
    private String moviePoster;
    private String overview;
    private String averageVote;

    public FavoriteMovie(String movieTitle, String movieID, String releaseDate, String moviePoster, String overview, String averageVote) {
        this.movieTitle = movieTitle;
        this.movieID = movieID;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.overview = overview;
        this.averageVote = averageVote;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getAverageVote() {
        return averageVote;
    }

    public String getMoviePoster() {
        return moviePoster;
    }
}
