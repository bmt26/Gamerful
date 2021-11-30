package com.example.gamezone.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gamezone.Adapters.ReviewsAdapter;
import com.example.gamezone.LoginActivity;
import com.example.gamezone.R;
import com.example.gamezone.models.Reviews;
import com.example.gamezone.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private User user;
    private TextView tvUsername;
    private ImageView ivProfilePicture;
    private Button btnEditProfile;
    private Button btnLogout;
    private List<Reviews> allReviews;
    private RecyclerView rvReviews;
    private ReviewsAdapter reviewsAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = (User) User.getCurrentUser();
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        rvReviews = view.findViewById(R.id.rvReviews);
        allReviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(getContext(), allReviews);

        tvUsername.setText(user.getUsername());

        ParseFile image = user.getProfileImage();
        if (image != null) {
            Glide.with(getContext())
                    .asBitmap()
                    .load(image.getUrl())
                    .centerCrop()
                    .circleCrop()
                    .into(ivProfilePicture);
        }
        else {
            Glide.with(getContext())
                    .load(R.drawable.ic_default_figure)
                    .into(ivProfilePicture);
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EditProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).addToBackStack(null).commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        rvReviews.setAdapter(reviewsAdapter);
        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        queryReviews();
    }



    private void queryReviews() {
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.include(Reviews.KEY_USER);
        query.whereEqualTo(Reviews.KEY_USER, ParseUser.getCurrentUser());
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
                reviewsAdapter.notifyDataSetChanged();

                Log.d(TAG, "All Reviews: " + allReviews);
            }
        });
    }
}
