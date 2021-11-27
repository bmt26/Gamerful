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
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String RECENT_GAME = "https://rawg.io/api/games/lists/recent-games";
    public static final String UPCOMING_GAMES = "https://rawg.io/api/games/lists/recent-games-future";
    public static final String TOP_RATED = "https://rawg.io/api/games/lists/main";
    public static final String TOP_THIS_YEAR = "https://rawg.io/api/games/lists/greatest";
    public static final String ALL_TIME_POPULAR = "https://rawg.io/api/games/lists/popular";
    public static final String PLATFORMS = "https://rawg.io/api/games";

    List<Games> topGames;
    List<Games> upcomingGames;
    List<Games> topRated;
    List<Games> thisMonth;
    List<Games> thisYear;
    List<Games> allTimePopular;
    List<Games> PC;
    List<Games> PS;
    List<Games> xBox;
    List<Games> android;
    List<Games> ios;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AsyncHttpClient client = new AsyncHttpClient();

        // Top Games this week

        RequestParams params = new RequestParams();
        params.put("discover", true);
        params.put("ordering", "-relevance");
        params.put("page", 1);
        params.put("page_size", 5);
        params.put("key", API_KEY);
        client.get(RECENT_GAME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results);
                    topGames = Games.fromJsonArray(results);
                    Log.d(TAG, "Top games: " + topGames.get(0).getName());
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

        // Top Rated

        params = new RequestParams();
        params.put("discover", true);
        params.put("ordering", "-relevance");
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(TOP_RATED, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Top Rated: " + results);
                    topRated = Games.fromJsonArray(results);
                    Log.d(TAG, "Top Rated: " + topRated.get(0).getName());
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

        // Top games this month

        params = new RequestParams();
        params.put("discover", true);
        params.put("ordering", "-added");
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(RECENT_GAME, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Top games this month: " + results);
                    thisMonth = Games.fromJsonArray(results);
                    Log.d(TAG, "Top games this month: " + thisMonth.get(0).getName());
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

        // Top games this year

        params = new RequestParams();
        params.put("discover", true);
        params.put("ordering", "-added");
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(TOP_THIS_YEAR, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Top games this year: " + results);
                    thisYear = Games.fromJsonArray(results);
                    Log.d(TAG, "Top games this year: " + thisYear.get(0).getName());
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

        // All Time greatest

        params = new RequestParams();
        params.put("discover", true);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(ALL_TIME_POPULAR, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "All Time greatest: " + results);
                    allTimePopular = Games.fromJsonArray(results);
                    Log.d(TAG, "All Time greatest: " + allTimePopular.get(0).getName());
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

        // Upcoming games

        params = new RequestParams();
        params.put("discover", true);
        params.put("ordering", "-added");
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(UPCOMING_GAMES, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results);
                    upcomingGames = Games.fromJsonArray(results);
                    Log.d(TAG, "Upcoming games: " + upcomingGames.get(0).getName());
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

        // PC

        params = new RequestParams();
        params.put("discover", true);
        params.put("filter", true);
        params.put("comments", true);
        params.put("parent_platforms", 1);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(PLATFORMS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "PC: " + results);
                    PC = Games.fromJsonArray(results);
                    Log.d(TAG, "PC: " + PC.get(0).getName());
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

        // PS

        params = new RequestParams();
        params.put("discover", true);
        params.put("filter", true);
        params.put("comments", true);
        params.put("parent_platforms", 2);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(PLATFORMS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "PS: " + results);
                    PS = Games.fromJsonArray(results);
                    Log.d(TAG, "PS: " + PS.get(0).getName());
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

        // xBox

        params = new RequestParams();
        params.put("discover", true);
        params.put("filter", true);
        params.put("comments", true);
        params.put("parent_platforms", 3);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(PLATFORMS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "xBox: " + results);
                    xBox = Games.fromJsonArray(results);
                    Log.d(TAG, "xBox: " + xBox.get(0).getName());
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

        // Android

        params = new RequestParams();
        params.put("discover", true);
        params.put("filter", true);
        params.put("comments", true);
        params.put("parent_platforms", 8);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(PLATFORMS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Android: " + results);
                    android = Games.fromJsonArray(results);
                    Log.d(TAG, "Android: " + android.get(0).getName());
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

        // IOS

        params = new RequestParams();
        params.put("discover", true);
        params.put("filter", true);
        params.put("comments", true);
        params.put("parent_platforms", 4);
        params.put("page", 1);
        params.put("page_size", 20);
        params.put("key", API_KEY);
        client.get(PLATFORMS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "IOS: " + results);
                    ios = Games.fromJsonArray(results);
                    Log.d(TAG, "IOS: " + ios.get(0).getName());
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
    }
}
