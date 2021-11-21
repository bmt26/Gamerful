package com.example.gamezone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gamezone.fragments.HomeFragment;
import com.example.gamezone.fragments.ProfileFragment;
import com.example.gamezone.fragments.ReviewFragment;
import com.example.gamezone.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();// define your fragments here
    static Fragment homeFragment = new HomeFragment();
    public Fragment searchFragment = new SearchFragment();
    public Fragment reviewFragment = new ReviewFragment();
    public Fragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.action_profile:
                    fragment = profileFragment;
                    break;
                case R.id.action_review:
                    fragment = reviewFragment;
                    break;
                case R.id.action_search:
                    fragment= searchFragment;
                    break;
                case R.id.action_home:
                default:
                    fragment = homeFragment;
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        });
        bottomNavigationView.setOnItemReselectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.action_profile:
                    profileFragment = new ProfileFragment();
                    fragment = profileFragment;
                    break;
                case R.id.action_review:
                    reviewFragment = new ReviewFragment();
                    fragment = reviewFragment;
                    break;
                case R.id.action_search:
                    searchFragment = new SearchFragment();
                    fragment= searchFragment;
                    break;
                case R.id.action_home:
                default:
                    homeFragment = new HomeFragment();
                    fragment = homeFragment;
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}