package com.example.gamezone.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gamezone.MainActivity;
import com.example.gamezone.R;
import com.example.gamezone.models.Reviews;
import com.example.gamezone.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeReviewFragment extends Fragment {

    public static final String TAG = "ComposeReviewFragment";
    private static final int RESULT_LOAD_IMG = 402;

    private Button btnBack;
    private Button btnSubmit;
    private Button btnAddImage;
    private RatingBar ratingBar;
    private EditText etComment;
    private ImageView ivImage;

    private Bitmap selectedImage;

    private ParseUser user;
    private String game;
    private String comment;
    private int starRating;

    private File photoFile;
    private String photoFileName = "photo.jpg";

    public ComposeReviewFragment() {
        // Required empty public constructor
    }

    public ComposeReviewFragment(String g) {
        // Required empty public constructor
        game = g;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.btnBack);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        etComment = view.findViewById(R.id.etComment);
        ratingBar = view.findViewById(R.id.ratingBar);
        ivImage = view.findViewById(R.id.ivImage);

        user = ParseUser.getCurrentUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDetailsFragment();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starRating = ratingBar.getNumStars();
                comment = etComment.getText().toString();
                if(comment.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
                    return;
                }
                addReview(user, game, comment, starRating);
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on image");
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }

    private void addReview(ParseUser user, String game, String comment, int starRating) {

        final ParseFile file = new ParseFile(photoFile);

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Reviews review = new Reviews();
                    review.setUser(user);
                    review.setGame(game);
                    review.setStartRating(starRating);
                    review.setComment(comment);
                    if(file!=null) {
                        review.setImage(file);
                    }
                    review.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(getContext(), "Error while posting review!", Toast.LENGTH_SHORT).show();
                            }
                            goMainActivity();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK && reqCode == RESULT_LOAD_IMG) {
            Log.d(TAG, "Result Code Inside: " + reqCode);
            try {
                final Uri imageUri = data.getData();
                Log.d(TAG, imageUri.toString());

                final InputStream imageStream = ((AppCompatActivity) getContext()).getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);

                photoFile = getPhotoFileUri(photoFileName);
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                Glide.with(getContext())
                        .asBitmap()
                        .load(takenImage)
                        .apply(new RequestOptions().override(takenImage.getWidth()*100/takenImage.getHeight(), 100))
                        .into(ivImage);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public File getPhotoFileUri(String fileName) throws IOException {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        File f = new File(mediaStorageDir.getPath(), fileName);
        f.createNewFile();

        //Convert bitmap to byte array
        Bitmap bitmap = selectedImage;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    private void goDetailsFragment() {
        getParentFragmentManager().popBackStack();
    }

    private void goMainActivity() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        ((AppCompatActivity) getContext()).finish();
    }
}