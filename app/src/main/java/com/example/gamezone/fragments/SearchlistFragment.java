package com.example.gamezone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.GamelistAdapter;
import com.example.gamezone.Adapters.ReviewsAdapter;
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

    RadioGroup rgCategory;
    RadioButton rbGames;
    RadioButton rbReviews;

    RecyclerView rvSearchReviews;
    RecyclerView rvSearchGames;

    TextView tvPageTitle;

    List<Games> games;
    List<Reviews> allReviews;

    ReviewsAdapter reviewsAdapter;
    GamelistAdapter gamelistAdapter;

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
        String url = BASE_URL + "&search=" + query;

        games = new ArrayList<>();
        allReviews = new ArrayList<>();

        rgCategory = view.findViewById(R.id.rgCategory);
        rbGames = view.findViewById(R.id.rbGames);
        rbReviews = view.findViewById(R.id.rbReviews);

        rvSearchGames = view.findViewById(R.id.rvSearchGames);
        rvSearchReviews = view.findViewById(R.id.rvSearchReviews);

        tvPageTitle = view.findViewById(R.id.tvPageTitle);

        gamelistAdapter = new GamelistAdapter(getContext(), games);
        rvSearchGames.setAdapter(gamelistAdapter);
        rvSearchGames.setLayoutManager(new LinearLayoutManager(getContext()));
        gamelistAdapter.notifyDataSetChanged();

        final FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        tvPageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.popBackStack();
            }
        });

        rbGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbGames.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getContext().getDrawable(R.drawable.ic_horizontal_line_svgrepo_com));
                rbReviews.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rvSearchGames.setVisibility(View.VISIBLE);
                rvSearchReviews.setVisibility(View.GONE);
            }
        });

        rbReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbReviews.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getContext().getDrawable(R.drawable.ic_horizontal_line_svgrepo_com));
                rbGames.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rvSearchGames.setVisibility(View.GONE);
                rvSearchReviews.setVisibility(View.VISIBLE);
                reviewsAdapter = new ReviewsAdapter(getContext(), allReviews);
                rvSearchReviews.setAdapter(reviewsAdapter);
                rvSearchReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                reviewsAdapter.notifyDataSetChanged();
            }
        });

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
                    gamelistAdapter.notifyDataSetChanged();
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
        game = game.split(":")[0].replaceAll("[^a-zA-Z]", "").toLowerCase();
        Log.d(TAG, game);
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.include(Reviews.KEY_USER);
        query.whereEqualTo("slug", game);
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
                Log.d(TAG, "All Reviews: " + allReviews);
            }
        });
    }
}