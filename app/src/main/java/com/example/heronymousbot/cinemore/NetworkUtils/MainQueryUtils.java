package com.example.heronymousbot.cinemore.NetworkUtils;

import android.text.TextUtils;
import android.util.Log;

import com.example.heronymousbot.cinemore.ClassesUtils.Films;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainQueryUtils {
    public static ArrayList<Films> fetchMovieData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = MainNetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e("QueryUtils: ", "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from the JSON response and create a list of obamaNews
        ArrayList<Films> moviesList = extractFeatureFromJson(jsonResponse);

        return moviesList;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Query Utils: ", "Problem building the URL ", e);
        }
        return url;
    }

    private static ArrayList<Films> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }


        ArrayList<Films> moviesList = new ArrayList<>();


        try {
            //Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);


            JSONArray movieArray = baseJsonResponse.getJSONArray("results");


            int i;
            for (i = 0; i < movieArray.length(); i++) {


                JSONObject currentMovie = movieArray.getJSONObject(i);


                String title = currentMovie.getString("title");


                double voteAverage = currentMovie.getDouble("vote_average");


                String posterPath = currentMovie.getString("poster_path");


                String releaseDate = currentMovie.getString("release_date");


                String overview = currentMovie.getString("overview");

                long movieID = currentMovie.getLong("id");


                Films mMovie = new Films(title, releaseDate, posterPath, voteAverage, overview, movieID);

                moviesList.add(mMovie);

            }

        } catch (JSONException e) {
            e.printStackTrace();

            //if an error is thrown when executing any of the above statements in the "try" block
            //catch the exception here, so the app doesn't crash.

            Log.e("QueryUtils", "Problem parsing the movies JSON results", e);
        }
        return moviesList;
    }


}

