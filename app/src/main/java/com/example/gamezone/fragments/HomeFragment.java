package com.example.gamezone.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.SliderAdapter;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String RECENT_GAME = "https://rawg.io/api/games/lists/recent-games?discover=true&ordering=-relevance&page_size=10&key=" + API_KEY;
    public static final String UPCOMING_GAMES = "https://rawg.io/api/games/lists/recent-games-future?discover=true&ordering=-added&page_size=20&page=1&key=" + API_KEY;
    public static final String TOP_RATED = "https://rawg.io/api/games/lists/main??discover=true&ordering=-relevance&page_size=20&page=1&key=" + API_KEY;
    public static final String TOP_THIS_YEAR = "https://rawg.io/api/games/lists/greatest?discover=true&ordering=-added&page_size=20&page=1&key=" + API_KEY;
    public static final String ALL_TIME_POPULAR = "https://rawg.io/api/games/lists/popular??discover=true&&page_size=20&page=1&key=" + API_KEY;
    public static final String PC_GAMES = "https://rawg.io/api/games?parent_platforms=1&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;
    public static final String PS_GAMES = "https://rawg.io/api/games?parent_platforms=2&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;
    public static final String XBOX_GAMES = "https://rawg.io/api/games?parent_platforms=3&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;
    public static final String ANDROID_GAMES = "https://rawg.io/api/games?parent_platforms=8&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;
    public static final String IOS_GAMES = "https://rawg.io/api/games?parent_platforms=4&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;

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

    LinearLayout dotsLayout;
    SliderAdapter slideAdapter;
    ViewPager2 pager2;
    TextView[] dots;

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

        topGames = new ArrayList<>();
        upcomingGames = new ArrayList<>();
        topRated = new ArrayList<>();
        thisMonth = new ArrayList<>();
        thisYear = new ArrayList<>();
        allTimePopular = new ArrayList<>();
        PC = new ArrayList<>();
        PS = new ArrayList<>();
        xBox = new ArrayList<>();
        android = new ArrayList<>();
        ios = new ArrayList<>();

        // Top Games this week
        dotsLayout = view.findViewById(R.id.dots_container);
        pager2 = view.findViewById(R.id.view_pager2);

        slideAdapter = new SliderAdapter(getContext(), topGames);
        pager2.setAdapter(slideAdapter);

        dots = new TextView[10];
        dotsIndicator();

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "position" + position);
                selectedIndicator(position);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

        client.get(RECENT_GAME, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results);
                    topGames.addAll(Games.fromJsonArray(results));
                    slideAdapter.notifyDataSetChanged();
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

        client.get(TOP_RATED, new JsonHttpResponseHandler() {
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

        client.get(RECENT_GAME, new JsonHttpResponseHandler() {
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

        client.get(TOP_THIS_YEAR, new JsonHttpResponseHandler() {
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

        client.get(ALL_TIME_POPULAR, new JsonHttpResponseHandler() {
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

        client.get(UPCOMING_GAMES, new JsonHttpResponseHandler() {
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

        client.get(PC_GAMES, new JsonHttpResponseHandler() {
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

        client.get(PS_GAMES, new JsonHttpResponseHandler() {
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

        client.get(XBOX_GAMES, new JsonHttpResponseHandler() {
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

        client.get(ANDROID_GAMES, new JsonHttpResponseHandler() {
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

        client.get(IOS_GAMES, new JsonHttpResponseHandler() {
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

    // update dot indicator for active image

    private void selectedIndicator(int position) {
        for(int i = 0; i < dots.length; i++) {
            if(i == position) {
                dots[i].setText(Html.fromHtml("&#9679;")); // &#9675;
            }
            else {
                dots[i].setText(Html.fromHtml("&#x25CB;")); // &#9675;
            }
        }
    }

    // Set dot indicator for image slide

    private void dotsIndicator() {
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#x25CB;")); // &#9675;
            dots[i].setTextSize(18);
            dotsLayout.addView(dots[i]);
        }
    }
}
