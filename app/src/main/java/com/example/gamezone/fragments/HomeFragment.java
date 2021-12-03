package com.example.gamezone.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.SliderAdapter;
import com.example.gamezone.Adapters.GamesAdapter;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String RECENT_GAME = "https://rawg.io/api/games/lists/recent-games?discover=true&ordering=-relevance&key=" + API_KEY + "&page_size=10";
    public static final String UPCOMING_GAMES = "https://rawg.io/api/games/lists/recent-games-future?discover=true&ordering=-added&page=1&key=" + API_KEY + "&page_size=20";
    public static final String GAMES_LAST_MONTH = "https://rawg.io/api/games/lists/recent-games-past?discover=true&ordering=-added&page=1&key=" + API_KEY + "&page_size=20";
    public static final String TOP_RATED = "https://rawg.io/api/games/lists/main?discover=true&ordering=-relevance&page=1&key=" + API_KEY + "&page_size=20";
    public static final String TOP_THIS_YEAR = "https://rawg.io/api/games/lists/greatest?discover=true&ordering=-added&page=1&key=" + API_KEY + "&page_size=20";
    public static final String ALL_TIME_POPULAR = "https://rawg.io/api/games/lists/popular??discover=true&page=1&key=" + API_KEY + "&page_size=20";
    public static final String PC_GAMES = "https://rawg.io/api/games?parent_platforms=1&ordering=-rating&page=1&filter=true&comments=true&key=" + API_KEY + "&page_size=20";
    public static final String PS_GAMES = "https://rawg.io/api/games?parent_platforms=2&ordering=-rating&page=1&filter=true&comments=true&key=" + API_KEY + "&page_size=20";
    public static final String XBOX_GAMES = "https://rawg.io/api/games?parent_platforms=3&ordering=-rating&page=1&filter=true&comments=true&key=" + API_KEY + "&page_size=20";
    public static final String ANDROID_GAMES = "https://rawg.io/api/games?parent_platforms=8&page=1&filter=true&comments=true&key=" + API_KEY + "&page_size=20";
    public static final String IOS_GAMES = "https://rawg.io/api/games?parent_platforms=4&page=1&filter=true&comments=true&key=" + API_KEY + "&page_size=20";

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
    LinearLayout linearHome;
    ShimmerFrameLayout shimmerFrameLayout;

    TextView tvViewAll1, tvViewAll2, tvViewAll3, tvViewAll4, tvViewAll5, tvViewAll6, tvViewAll7, tvViewAll8, tvViewAll9, tvViewAll10;

    FragmentManager fragmentManager;

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

        tvViewAll1 = view.findViewById(R.id.tvViewAll1);
        tvViewAll2 = view.findViewById(R.id.tvViewAll2);
        tvViewAll3 = view.findViewById(R.id.tvViewAll3);
        tvViewAll4 = view.findViewById(R.id.tvViewAll4);
        tvViewAll5 = view.findViewById(R.id.tvViewAll5);
        tvViewAll6 = view.findViewById(R.id.tvViewAll6);
        tvViewAll7 = view.findViewById(R.id.tvViewAll7);
        tvViewAll8 = view.findViewById(R.id.tvViewAll8);
        tvViewAll9 = view.findViewById(R.id.tvViewAll9);
        tvViewAll10 = view.findViewById(R.id.tvViewAll10);

        tvViewAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(TOP_RATED, "Top Rated");
            }
        });

        tvViewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(GAMES_LAST_MONTH, "This Month");
            }
        });

        tvViewAll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(TOP_THIS_YEAR, "This Year");
            }
        });

        tvViewAll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(ALL_TIME_POPULAR, "All Time Greatest");
            }
        });

        tvViewAll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(UPCOMING_GAMES, "Upcoming");
            }
        });

        tvViewAll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(PC_GAMES, "PC Games");
            }
        });

        tvViewAll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(PS_GAMES, "Play Station Games");
            }
        });

        tvViewAll8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(XBOX_GAMES, "X-Box Games");
            }
        });

        tvViewAll9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(ANDROID_GAMES, "Android Games");
            }
        });

        tvViewAll10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGamelistFragment(IOS_GAMES, "IOS Games");
            }
        });

        linearHome = view.findViewById(R.id.linearHome);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutHome);

        shimmerFrameLayout.startShimmer();

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
                selectedIndicator(position);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

        makeRequest(RECENT_GAME, topGames, slideAdapter);

        // Top Rated

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager topRatedManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvTopGames);
        recyclerView.setLayoutManager(topRatedManager);
        GamesAdapter gamesAdapter = new GamesAdapter(getContext(), topRated);
        recyclerView.setAdapter(gamesAdapter);

        makeGameRequest(TOP_RATED, topRated, gamesAdapter);


        // Top games this month

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager topMonthManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvTopMonth = view.findViewById(R.id.rvTopMonth);
        rvTopMonth.setLayoutManager(topMonthManager);
        GamesAdapter gamesAdapter2 = new GamesAdapter(getContext(), thisMonth);
        rvTopMonth.setAdapter(gamesAdapter2);

        makeGameRequest(GAMES_LAST_MONTH, thisMonth, gamesAdapter2);

        // Top games this year

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager topYearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvTopYear = view.findViewById(R.id.rvTopYear);
        rvTopYear.setLayoutManager(topYearManager);
        GamesAdapter gamesAdapter3 = new GamesAdapter(getContext(), thisYear);
        rvTopYear.setAdapter(gamesAdapter3);

        makeGameRequest(TOP_THIS_YEAR, thisYear, gamesAdapter3);


        // All Time greatest

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager allGreatestManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvGreatest = view.findViewById(R.id.rvGreatest);
        rvGreatest.setLayoutManager(allGreatestManager);
        GamesAdapter gamesAdapter4 = new GamesAdapter(getContext(), allTimePopular);
        rvGreatest.setAdapter(gamesAdapter4);

        makeGameRequest(ALL_TIME_POPULAR, allTimePopular, gamesAdapter4);

        // Upcoming games

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager upcomingManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvUpcoming.setLayoutManager(upcomingManager);
        GamesAdapter gamesAdapter5 = new GamesAdapter(getContext(), upcomingGames);
        rvUpcoming.setAdapter(gamesAdapter5);

        makeGameRequest(UPCOMING_GAMES, upcomingGames, gamesAdapter5);

        // PC

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager pcGameManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvPcGames = view.findViewById(R.id.rvPcGames);
        rvPcGames.setLayoutManager(pcGameManager);
        GamesAdapter gamesAdapter6 = new GamesAdapter(getContext(), PC);
        rvPcGames.setAdapter(gamesAdapter6);

        makeGameRequest(PC_GAMES, PC, gamesAdapter6);


        // PS

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager psGameManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvPsGames = view.findViewById(R.id.rvPsGames);
        rvPsGames.setLayoutManager(psGameManager);
        GamesAdapter gamesAdapter7 = new GamesAdapter(getContext(), PS);
        rvPsGames.setAdapter(gamesAdapter7);

        makeGameRequest(PS_GAMES, PS, gamesAdapter7);


        // xBox

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager xBoxGameManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvXBoxGames = view.findViewById(R.id.rvXBoxGames);
        rvXBoxGames.setLayoutManager(xBoxGameManager);
        GamesAdapter gamesAdapter8 = new GamesAdapter(getContext(), xBox);
        rvXBoxGames.setAdapter(gamesAdapter8);

        makeGameRequest(XBOX_GAMES, xBox, gamesAdapter8);


        // Android

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager androidGameManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvAndroidGames = view.findViewById(R.id.rvAndroidGames);
        rvAndroidGames.setLayoutManager(androidGameManager);
        GamesAdapter gamesAdapter9 = new GamesAdapter(getContext(), android);
        rvAndroidGames.setAdapter(gamesAdapter9);

        makeGameRequest(ANDROID_GAMES, android, gamesAdapter9);

        // IOS

        // Create a new instance of adapter and set it to the recyclerview
        LinearLayoutManager iosGameManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvIosGames = view.findViewById(R.id.rvIosGames);
        rvIosGames.setLayoutManager(iosGameManager);
        GamesAdapter gamesAdapter10 = new GamesAdapter(getContext(), ios);
        rvIosGames.setAdapter(gamesAdapter10);

        makeGameRequest(IOS_GAMES, ios, gamesAdapter10);
    }

    private void launchGamelistFragment(String Url, String name) {
        int index=Url.lastIndexOf('=');
        Url = Url.substring(0, index) + "=40";
        fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("Url", Url);
        bundle.putString("Name", name);

        Fragment fragment = new GamelistFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Make rawg api get request for coracle
    private void makeRequest(String url, List<Games> games, SliderAdapter adapter) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    games.addAll(Games.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
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

    // Makes API requests for horizontal recycle view
    private void makeGameRequest(String url, List<Games> games, GamesAdapter adapter) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    games.addAll(Games.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    if(url.equals(IOS_GAMES)) {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        linearHome.setVisibility(View.VISIBLE);
                    }
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
            dots[i].setTextSize(10);
            dotsLayout.addView(dots[i]);
        }
    }
}
