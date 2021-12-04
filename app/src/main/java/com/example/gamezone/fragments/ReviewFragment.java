package com.example.gamezone.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.gamezone.Adapters.ReviewsAdapter;
import com.example.gamezone.R;
import com.example.gamezone.models.Reviews;
import com.example.gamezone.models.User;
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
    protected List<Reviews> parentsReviews;
    protected List<Reviews> kidsReviews;
    private RecyclerView rvReviews;
    private ReviewsAdapter reviewsAdapter;
    private RadioGroup rgAge;
    private RadioButton rbParents;
    private RadioButton rbKids;
    private ProgressBar pbReviews;

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
        super.onViewCreated(view, savedInstanceState);
        rbParents = view.findViewById(R.id.rbParents);
        rbKids = view.findViewById(R.id.rbKids);
        pbReviews = view.findViewById(R.id.pbReviews);

        pbReviews.setVisibility(View.VISIBLE);

        rbParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbParents.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getContext().getDrawable(R.drawable.ic_horizontal_line_svgrepo_com));
                rbKids.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                reviewsAdapter = new ReviewsAdapter(getContext(), parentsReviews);
                rvReviews.setAdapter(reviewsAdapter);
                rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                reviewsAdapter.notifyDataSetChanged();
                pbReviews.setVisibility(View.GONE);
            }
        });

        rbKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbKids.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getContext().getDrawable(R.drawable.ic_horizontal_line_svgrepo_com));
                rbParents.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                reviewsAdapter = new ReviewsAdapter(getContext(), kidsReviews);
                rvReviews.setAdapter(reviewsAdapter);
                rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                reviewsAdapter.notifyDataSetChanged();
                pbReviews.setVisibility(View.GONE);
            }
        });
        rvReviews = view.findViewById(R.id.rbReviews);
        parentsReviews = new ArrayList<>();
        kidsReviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(getContext(), parentsReviews);
        rvReviews.setAdapter(reviewsAdapter);
        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
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
                parentsReviews.clear();
                kidsReviews.clear();
                for(Reviews review : reviews) {
                    User user = (User)review.getUser();
                    if (user.getRole().equals("Parent")) {
                        parentsReviews.add(review);
                    }
                    else {
                        kidsReviews.add(review);
                    }
                }
                reviewsAdapter.notifyDataSetChanged();

                Log.d(TAG, "Parents Reviews: " + parentsReviews);
                Log.d(TAG, "Kids Reviews: " + kidsReviews);
            }
        });
    }
}
