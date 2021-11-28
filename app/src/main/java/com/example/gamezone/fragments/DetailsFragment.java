package com.example.gamezone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class DetailsFragment extends Fragment {

    TextView tvId;

    public static final String TAG = "DetailsFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String BASE_URL = "https://rawg.io/api/games/";

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvId = view.findViewById(R.id.tvId);

        Bundle bundle = this.getArguments();
        int gameId = bundle.getInt("Id");
        tvId.setText(String.valueOf(gameId));

        String details_url = BASE_URL + String.valueOf(gameId) + "?key=" + API_KEY;
        String store_url = BASE_URL + String.valueOf(gameId) + "/stores?key=" + API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(details_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                Log.i(TAG, "Results: " + jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        client.get(store_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                Log.i(TAG, "Results: " + jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}