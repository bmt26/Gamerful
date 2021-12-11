package com.example.gamezone.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.gamezone.Adapters.ScreenshotAdapter;
import com.example.gamezone.Adapters.StoresAdapter;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.models.Screenshots;
import com.example.gamezone.models.Stores;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    String clip;

    int esrbImage;

    int reviewCount;
    int playTime;
    int metacritic;
    double ratings;

    Boolean expanded;

    List<Screenshots> screenshots;
    List<Stores> stores;

    ImageView imgGame;
    TextView gameName;
    TextView gameGenre;
    TextView gameRating;
    TextView ratingCount;
    TextView tvPlayTime;
    ImageView ivEsrb;
    TextView tvEsrbRating;
    TextView tvBuy;
    TextView tvDescription;
    TextView tvReleaseData;
    TextView tvAgeRating;
    TextView tvPublisher;
    TextView tvMetaScore;
    Button reviewBtn;
    ShimmerFrameLayout shimmerFrameLayout;
    ConstraintLayout scrollView2;
    Button btnSeeMore;
    CardView playTag;

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

        expanded = false;

        imgGame = view.findViewById(R.id.imgGame);
        gameName = view.findViewById(R.id.gameName);
        gameGenre = view.findViewById(R.id.gameGenre);
        gameRating = view.findViewById(R.id.gameRating);
        ratingCount = view.findViewById(R.id.ratingCount);
        tvPlayTime = view.findViewById(R.id.tvPlayTime);
        ivEsrb = view.findViewById(R.id.ivEsrb);
        tvEsrbRating = view.findViewById(R.id.tvEsrbRating);
        reviewBtn = view.findViewById(R.id.reviewBtn);
        tvBuy = view.findViewById(R.id.tvBuy);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvReleaseData = view.findViewById(R.id.tvReleaseData);
        tvAgeRating = view.findViewById(R.id.tvAgeRating);
        tvPublisher = view.findViewById(R.id.tvPublisher);
        tvMetaScore = view.findViewById(R.id.tvMetaScore);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        scrollView2 = view.findViewById(R.id.scrollView2);
        btnSeeMore = view.findViewById(R.id.btnSeeMore);
        playTag = view.findViewById(R.id.playTag);

        shimmerFrameLayout.startShimmer();

        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();

        Bundle bundle = this.getArguments();
        int gameId = bundle.getInt("Id");

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ComposeReviewFragment(name);
                getParentFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).addToBackStack(null).commit();
            }
        });

        String details_url = BASE_URL + String.valueOf(gameId) + "?key=" + API_KEY;
        String store_url = BASE_URL + String.valueOf(gameId) + "/stores?key=" + API_KEY;
        String screenshots_url = BASE_URL + String.valueOf(gameId) + "/screenshots?key=" + API_KEY;

        Log.d(TAG, details_url);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvScreenshots);
        recyclerView.setLayoutManager(layoutManager);
        ScreenshotAdapter adapter = new ScreenshotAdapter(getContext(), screenshots);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView2 = view.findViewById(R.id.rvStores);
        recyclerView2.setLayoutManager(layoutManager2);
        StoresAdapter storesAdapter = new StoresAdapter(getContext(), stores);
        recyclerView2.setAdapter(storesAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(details_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
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
                        clip = jsonObject.getJSONObject("clip").getString("clip");
                    } catch (JSONException e) {
                        clip = null;
                    }

                    try {
                        esrbRating = jsonObject.getJSONObject("esrb_rating").getString("name");
                    } catch (JSONException e) {
                        esrbRating = "Rating Pending";
                    }

                    try {
                        metacritic = jsonObject.getInt("metacritic");
                    } catch (JSONException e) {
                        metacritic = 0;
                    }

                    gameName.setText(name);
                    gameGenre.setText(genres);
                    gameRating.setText(String.valueOf(ratings));
                    ratingCount.setText(reviewCount + " reviews");
                    tvPlayTime.setText(String.valueOf(playTime));
                    tvEsrbRating.setText(esrbRating);
                    tvDescription.setText(description);
                    tvReleaseData.setText(releaseDate);
                    tvAgeRating.setText(esrbRating);
                    tvPublisher.setText(publisher);
                    tvMetaScore.setText(String.valueOf(metacritic));

                    Log.d(TAG, "Clip: " +  clip);

                    tvDescription.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            if (tvDescription.getLineCount() >= 7 && !expanded) {
                                btnSeeMore.setVisibility(View.VISIBLE);

                            }
                        }
                    });

                    btnSeeMore.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            if (!expanded) {
                                expanded = true;
                                ObjectAnimator animation = ObjectAnimator.ofInt(tvDescription, "maxLines", 40);
                                animation.setDuration(100).start();
                                btnSeeMore.setText("Show Less...");
                            } else {
                                expanded = false;
                                ObjectAnimator animation = ObjectAnimator.ofInt(tvDescription, "maxLines", 7);
                                animation.setDuration(100).start();
                                btnSeeMore.setText("Show More...");
                            }

                        }
                    });

                    switch (esrbRating) {
                        case "Everyone":
                            esrbImage = R.drawable.ic_esrb_e;
                            break;

                        case "Everyone 10+":
                            esrbImage = R.drawable.ic_esrb_e10;
                            break;

                        case "Teen":
                            esrbImage = R.drawable.ic_esrb_t;
                            break;

                        case "Mature":
                            esrbImage = R.drawable.ic_esrb_m;
                            break;

                        case "Adults Only":
                            esrbImage = R.drawable.ic_esrb_a18;
                            break;

                        case "Rating Pending":
                            esrbImage = R.drawable.ic_esrb_rp;
                            break;
                    }

                    Glide.with(getContext())
                            .asBitmap()
                            .centerCrop()
                            .load(esrbImage)
                            .into(ivEsrb);

                    // Gets object list of stores
                    client.get(store_url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.d(TAG, "onSuccess");
                            JSONObject jsonObjectStore = json.jsonObject;
                            try {
                                JSONArray results = jsonObjectStore.getJSONArray("results");
                                JSONArray store_list = jsonObject.getJSONArray("stores");
                                Log.d(TAG, "List: " + store_list);
                                stores.addAll(Stores.fromJsonArray(results, store_list));

                                if(stores.size() == 0) {
                                    tvBuy.setVisibility(View.GONE);
                                }
                                else {
                                    tvBuy.setVisibility(View.VISIBLE);
                                }

                                storesAdapter.notifyDataSetChanged();

                                shimmerFrameLayout.setVisibility(View.GONE);
                                scrollView2.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.d(TAG, "onFailure");
                        }
                    });
                    Glide.with(getContext())
                            .load(poster)
                            .centerCrop()
                            .into(imgGame);

                    if(clip != null) {
                        playTag.setVisibility(View.VISIBLE);
                        imgGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("clip", clip);

                                Fragment fragment = new ClipFragment();
                                fragment.setArguments(bundle);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.flContainer, fragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                    }
                    else {
                        playTag.setVisibility(View.GONE);
                    }

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
                    adapter.notifyDataSetChanged();
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