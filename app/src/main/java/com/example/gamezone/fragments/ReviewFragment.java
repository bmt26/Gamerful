package com.example.gamezone.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamezone.R;
import com.example.gamezone.models.Reviews;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {


    private static final String TAG = "ReviewFragment";
    protected List<Reviews> allReviews;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        allReviews = new ArrayList<>();
        queryReviews();
    }

    protected void queryReviews() {
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.include(Reviews.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Reviews.KEY_CREATED_KEY);

        query.findInBackground(new FindCallback<Reviews>() {
            @Override
            public void done(List<Reviews> reviews, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                allReviews.clear();
                allReviews.addAll(reviews);

                Log.d(TAG, "All Reviews: " + allReviews);
            }
        });
    }
}
