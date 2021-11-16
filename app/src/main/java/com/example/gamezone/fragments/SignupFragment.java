package com.example.gamezone.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamezone.R;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SignupFragment extends Fragment {

    private final String TAG = "SignupFragment";
    private static final int RESULT_LOAD_IMG = 402;
    private ImageView ivAddPicture;
    private EditText etAddUsername;
    private EditText etAddFullname;
    private EditText etAddPassword;
    private EditText etConfirmPassword;
    private TextView signupTextView;

    private Bitmap selectedImage;

    private String username;
    private String fullName;
    private String bio;
    private String password;
    private String confirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivAddPicture = view.findViewById(R.id.ivAddPicture);
        etAddUsername = view.findViewById(R.id.etAddUsername);
        etAddPassword = view.findViewById(R.id.etAddPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);


        final FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();


        ivAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on image");
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        view.findViewById(R.id.signupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etAddUsername.getText().toString();
                fullName = etAddFullname.getText().toString();
                password = etAddPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                if(username.isEmpty() || fullName.isEmpty() || bio.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(getContext(), "Your password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}