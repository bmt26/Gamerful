package com.example.gamezone.progressButtonSubmit;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamezone.R;

public class ProgressButtonLogin {

    private ProgressBar progressBar;
    private TextView textView;

    public ProgressButtonLogin(Context context, View view) {
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.tvLoginBtn);
    }

    public void buttonActivated() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
    }

    public void buttonReset() {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }
}
