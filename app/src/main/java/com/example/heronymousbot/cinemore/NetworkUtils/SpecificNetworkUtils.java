package com.example.heronymousbot.cinemore.NetworkUtils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class SpecificNetworkUtils {

    private final static String MOVIEDB_BASE_URL =
            "https://api.themoviedb.org/3/movie";


    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    private final static String API_KEY = "api_key";
    private final static String LANGUAGE = "language";
    private final static String REVIEWS_KEY = "reviews";
    private final static String TRAILERS_KEY = "videos";


    public static URL buildReviewsUrl(String movieID) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath(REVIEWS_KEY)
                .appendQueryParameter(API_KEY, "ad376c0763ee7fc1330f8182f81ff705")
                .appendQueryParameter(LANGUAGE, "en-US")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTrailersUrl(String movieID) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath(TRAILERS_KEY)
                .appendQueryParameter(API_KEY, "ad376c0763ee7fc1330f8182f81ff705")
                .appendQueryParameter(LANGUAGE, "en-US")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
