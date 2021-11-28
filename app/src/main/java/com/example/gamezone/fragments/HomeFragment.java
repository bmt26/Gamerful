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
import java.util.Collection;
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

        makeRequest(RECENT_GAME, topGames, slideAdapter);

        // Top Rated
        makeRequest(TOP_RATED, topRated, slideAdapter);


        // Top games this month
        makeRequest(RECENT_GAME, thisMonth, slideAdapter);

        // Top games this year
        makeRequest(TOP_THIS_YEAR, thisYear, slideAdapter);


        // All Time greatest
        makeRequest(ALL_TIME_POPULAR, allTimePopular, slideAdapter);

        // Upcoming games
        makeRequest(UPCOMING_GAMES, upcomingGames, slideAdapter);

        // PC
        makeRequest(PC_GAMES, PC, slideAdapter);


        // PS
        makeRequest(PS_GAMES, PS, slideAdapter);


        // xBox
        makeRequest(XBOX_GAMES, xBox, slideAdapter);


        // Android
        makeRequest(ANDROID_GAMES, android, slideAdapter);

        // IOS
        makeRequest(IOS_GAMES, ios, slideAdapter);
    }

    // Make rawg api get request
    private void makeRequest(String url, List<Games> games, SliderAdapter adapter) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results);
                    games.addAll(Games.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "games: " + games.get(0).getName());
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
