package com.example.heronymousbot.cinemore.ClassesUtils;

public class Films {
    private String title;
    private String releaseDate;
    private String moviePoster;
    private String overview;
    private String averageVote;
    private String movieID;

    public Films(String title, String releaseDate, String moviePoster, double averageVote, String overview, long id) {

        this.title = title;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.averageVote = String.valueOf(averageVote);
        this.overview = overview;
        this.movieID = String.valueOf(id);


    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMoviePoster() {
        return "http://image.tmdb.org/t/p/" + "w185" + moviePoster;
    }

    public String getAverageVote() {
        return averageVote;
    }

    public String getOverview() {
        return overview;
    }

    public String getMovieID() {
        return movieID;
    }
}
