package com.example.gamezone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;
import com.example.gamezone.models.Reviews;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchlistFragment extends Fragment {

    private static final String TAG = "SearchlistFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String BASE_URL = "https://rawg.io/api/games?key=" + API_KEY;
    String query;

    List<Games> games;
    private List<Reviews> allReviews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        query = bundle.getString("query");
        String url = BASE_URL + "&" + query;

        games = new ArrayList<>();
        allReviews = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    games.addAll(Games.fromJsonArray(results));
                    Log.i(TAG, "Search Results:" + results);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        queryReviews(query);

        
    }

    private void queryReviews(String game) {
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.include(Reviews.KEY_USER);
        query.whereEqualTo("game", game);
        query.setLimit(20);
        query.addDescendingOrder(Reviews.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Reviews>() {
            @Override
            public void done(List<Reviews> reviews, ParseException e) {
                if (e!= null) {
                    Log.e(TAG, "Issue with getting reviews", e);
                    return;
                }
                allReviews.clear();
                allReviews.addAll(reviews);

                Log.d(TAG, "All Reviews: " + allReviews.get(0).getComment());
            }
        });
    }
}