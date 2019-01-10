package com.example.heronymousbot.cinemore.NetworkUtils;

import android.text.TextUtils;
import android.util.Log;

import com.example.heronymousbot.cinemore.ClassesUtils.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class TrailersQueryUtils {
    public static ArrayList<Trailers> fetchTrailerData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = SpecificNetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e("QueryUtils: ", "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from the JSON response and create a list of obamaNews
        ArrayList<Trailers> trailersList = extractFeatureFromJson(jsonResponse);

        return trailersList;
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

    private static ArrayList<Trailers> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }


        ArrayList<Trailers> trailersList = new ArrayList<>();


        try {
            //Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);


            JSONArray trailerArray = baseJsonResponse.getJSONArray("results");


            int i;
            for (i = 0; i < trailerArray.length(); i++) {


                JSONObject currentTrailer = trailerArray.getJSONObject(i);


                String youtubeKey = currentTrailer.getString("key");


                Trailers mTrailer = new Trailers(youtubeKey);

                trailersList.add(mTrailer);

            }

        } catch (JSONException e) {
            e.printStackTrace();

            //if an error is thrown when executing any of the above statements in the "try" block
            //catch the exception here, so the app doesn't crash.

            Log.e("QueryUtils", "Problem parsing the reviews JSON results", e);
        }
        return trailersList;
    }


}
