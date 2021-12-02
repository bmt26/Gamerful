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
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.GamelistAdapter;
import com.example.gamezone.EndlessRecyclerViewScrollListener;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class GamelistFragment extends Fragment {

    private static final String TAG = "GamelistFragment";

    List<Games> games;
    String url;
    String name;

    RecyclerView recyclerView;
    EndlessRecyclerViewScrollListener scrollListener;

    TextView tvPageTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gamelist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        games = new ArrayList<>();

        tvPageTitle = view.findViewById(R.id.tvPageTitle);

        final FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        Bundle bundle = this.getArguments();
        url = bundle.getString("Url");
        name = bundle.getString("Name");

        tvPageTitle.setText(name);

        tvPageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.popBackStack();
            }
        });

        int index=url.lastIndexOf('=');
        url = url.substring(0, index) + "=40";

        Log.i(TAG, url);

        recyclerView = view.findViewById(R.id.rvGameList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        GamelistAdapter adapter = new GamelistAdapter(getContext(), games);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "Load: " + page);
                loadMoreData(games, url, adapter, page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

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
                    Log.d(TAG, "onSuccess" + games.size());
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

    private void loadMoreData(List<Games> games, String url, GamelistAdapter adapter, int page) {

        url += ("&page= " + (page + 1));

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
                    Log.d(TAG, "onSuccess" + games.size());
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