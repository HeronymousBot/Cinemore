package com.example.heronymousbot.cinemore.NetworkUtils;

import android.text.TextUtils;
import android.util.Log;

import com.example.heronymousbot.cinemore.ClassesUtils.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ReviewQueryUtils {
    public static ArrayList<Reviews> fetchReviewData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = SpecificNetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e("QueryUtils: ", "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from the JSON response and create a list of obamaNews
        ArrayList<Reviews> reviewsList = extractFeatureFromJson(jsonResponse);

        return reviewsList;
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

    private static ArrayList<Reviews> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }


        ArrayList<Reviews> reviewsList = new ArrayList<>();


        try {
            //Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);


            JSONArray reviewArray = baseJsonResponse.getJSONArray("results");


            int i;
            for (i = 0; i < reviewArray.length(); i++) {


                JSONObject currentReview = reviewArray.getJSONObject(i);


                String author = currentReview.getString("author");
                String content = currentReview.getString("content");
                String reviewUrl = currentReview.getString("url");


                Reviews mReview = new Reviews(author, content, reviewUrl);

                reviewsList.add(mReview);

            }

        } catch (JSONException e) {
            e.printStackTrace();

            //if an error is thrown when executing any of the above statements in the "try" block
            //catch the exception here, so the app doesn't crash.

            Log.e("QueryUtils", "Problem parsing the reviews JSON results", e);
        }
        return reviewsList;
    }


}


