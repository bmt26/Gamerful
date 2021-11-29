package com.example.gamezone.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Screenshots;
import com.example.gamezone.models.Stores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailsFragment extends Fragment {



    String name;
    String genres;
    String esrbRating;
    String poster;
    String description;
    String releaseDate;
    String publisher;

    int reviewCount;
    int playTime;
    int metacritic;
    double ratings;

    List<Screenshots> screenshots;
    List<Stores> stores;

    ImageView imgGame;
    TextView gameName;
    TextView gameGenre;
    TextView gameRating;
    TextView ratingCount;
    TextView tvPlayTime;
    TextView tvEsrbRating;
    Button reviewBtn;

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

        screenshots = new ArrayList<>();
        stores = new ArrayList<>();

        imgGame = view.findViewById(R.id.imgGame);
        gameName = view.findViewById(R.id.gameName);
        gameGenre = view.findViewById(R.id.gameGenre);
        gameRating = view.findViewById(R.id.gameRating);
        ratingCount = view.findViewById(R.id.ratingCount);
        tvPlayTime = view.findViewById(R.id.tvPlayTime);
        tvEsrbRating = view.findViewById(R.id.tvEsrbRating);
        reviewBtn = view.findViewById(R.id.reviewBtn);

        Bundle bundle = this.getArguments();
        int gameId = bundle.getInt("Id");

        String details_url = BASE_URL + String.valueOf(gameId) + "?key=" + API_KEY;
        String store_url = BASE_URL + String.valueOf(gameId) + "/stores?key=" + API_KEY;
        String screenshots_url = BASE_URL + String.valueOf(gameId) + "/screenshots?key=" + API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(details_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                Log.i(TAG, "Results: " + jsonObject);
                try {
                    name = jsonObject.getString("name");
                    poster = jsonObject.getString("background_image");
                    ratings = jsonObject.getDouble("rating");
                    reviewCount = jsonObject.getInt("ratings_count");

                    description = jsonObject.getString("description_raw");
                    releaseDate = jsonObject.getString("released");
                    publisher = getPublishers(jsonObject.getJSONArray("publishers"));
                    genres = getGenres(jsonObject.getJSONArray("genres"));
                    playTime = jsonObject.getInt("playtime");
                    try {
                        esrbRating = jsonObject.getJSONObject("esrb_rating").getString("name");
                    } catch (JSONException e) {
                        esrbRating = "N/A";
                    }
                    try {
                        metacritic = jsonObject.getInt("metacritic");
                    } catch (JSONException e) {
                        metacritic = 0;
                    }

                    // Gets object list of stores
                    client.get(store_url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.d(TAG, "onSuccess");
                            JSONObject jsonObjectStore = json.jsonObject;
                            try {
                                JSONArray results = jsonObjectStore.getJSONArray("results");
                                JSONArray store_list = jsonObject.getJSONArray("stores");
                                stores.addAll(Stores.fromJsonArray(results, store_list));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.i(TAG, "Results: " + stores.get(0).store_name + " : " + stores.get(0).store_url);
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.d(TAG, "onFailure");
                        }
                    });
                    Glide.with(getContext()).load(poster).centerCrop().into(imgGame);
                    gameName.setText(name);
                    gameGenre.setText(genres);
                    gameRating.setText(String.valueOf(ratings));
                    ratingCount.setText(reviewCount + " reviews");
                    tvPlayTime.setText(String.valueOf(playTime));
                    tvEsrbRating.setText(esrbRating);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        client.get(screenshots_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    screenshots.addAll(Screenshots.fromJsonArray(results));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private String getGenres(JSONArray genres) throws JSONException {
        List<String> genreList = new ArrayList<>();

        for(int i = 0; i < genres.length(); i++) {
            genreList.add(genres.getJSONObject(i).getString("name"));
        }

        return TextUtils.join(" · ", genreList);
    }

    private String getPublishers(JSONArray publishers) throws JSONException {
        List<String> publisherList = new ArrayList<>();

        for(int i = 0; i < publishers.length(); i++) {
            publisherList.add(publishers.getJSONObject(i).getString("name"));
        }
        return TextUtils.join(" , ", publisherList);
    }
}