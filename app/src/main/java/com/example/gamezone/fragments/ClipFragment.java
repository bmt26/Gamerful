package com.example.gamezone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.example.gamezone.R;

public class ClipFragment extends Fragment {

    private static final String TAG = "ClipFragment";

    private VideoView videoView;
    private Button close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        String clip = bundle.getString("clip");

        videoView = view.findViewById(R.id.videoView);
        close = view.findViewById(R.id.btnClose);

        videoView.setVideoPath(clip);
        videoView.start();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

    }

    private void closeFragment() {
        getParentFragmentManager().popBackStack();
    }
}