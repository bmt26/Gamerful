package com.example.gamezone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.GenreAdapter;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;
import com.example.gamezone.models.Genres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    public static final String API_KEY = BuildConfig.RAWG_KEY;
    public static final String URL = "https://api.rawg.io/api/genres?key=" + API_KEY;

    public static final String action_url = "https://rawg.io/api/games?disable_user_platforms=true&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY + "&genres=4";

    List<Genres> genres;

    EditText etSearch;
    RecyclerView rvGenrelist;

    GenreAdapter adapter;

    FragmentManager fragmentManager;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        genres = new ArrayList<>();

        etSearch = view.findViewById(R.id.etSearch);
        rvGenrelist = view.findViewById(R.id.rvGenrelist);

        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String query = String.valueOf(etSearch.getText());
                    launchSearchlistFragment(query);
                    etSearch.getText().clear();
                    return true;
                }
                return false;
            }
        });

        adapter = new GenreAdapter(getContext(), genres);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvGenrelist.setLayoutManager(gridLayoutManager);
        rvGenrelist.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    genres.addAll(Genres.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure");
            }
        });

    }

    private void launchSearchlistFragment(String query) {
        fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("query", query);

        Fragment fragment = new SearchlistFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}