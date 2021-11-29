package com.example.gamezone.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.gamezone.MainActivity;
import com.example.gamezone.R;
import com.example.gamezone.models.User;
import com.parse.LogInCallback;
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


// TODO: Repurpose Signup to EditProfile
public class EditProfileFragment extends Fragment {

    private final String TAG = "EditProfileFragment";

    private User user;
    private static final int RESULT_LOAD_IMG = 402;
    private ImageView ivNewPicture;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private EditText etOldPassword;
    private RadioButton radioButton;
    private Button btnBack;
    private Button btnSave;

    private Bitmap selectedImage;

    private String username;
    private String role;
    private String newPassword;
    private String confirmNewPassword;


    RadioGroup radioGroup;

    private File photoFile;
    private String photoFileName = "photo.jpg";

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = (User) User.getCurrentUser();
        ivNewPicture = view.findViewById(R.id.ivNewPicture);
        etNewUsername = view.findViewById(R.id.etNewUsername);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmNewPassword = view.findViewById(R.id.etConfirmNewPassword);
        radioGroup = view.findViewById(R.id.radioGroup);
        btnBack = view.findViewById(R.id.btnBack);
        btnSave = view.findViewById(R.id.btnSave);

        etNewUsername.setText(user.getUsername());

        if (user.getRole().equals("Parent")) {
            radioButton = view.findViewById(R.id.radioYes);
        }
        else {
            radioButton = view.findViewById(R.id.radioNo);
        }
        radioButton.setChecked(true);

        ParseFile image = user.getProfileImage();
        if (image != null) {
            Glide.with(getContext())
                    .asBitmap()
                    .load(image.getUrl())
                    .centerCrop()
                    .circleCrop()
                    .into(ivNewPicture);
        }
        else {
            Glide.with(getContext())
                    .load(R.drawable.ic_default_figure)
                    .into(ivNewPicture);
        }

        ivNewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked on image");
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfileFragment();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etNewUsername.getText().toString();
                newPassword = etNewPassword.getText().toString();
                confirmNewPassword = etConfirmNewPassword.getText().toString();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);

                switch (radioButton.getText().toString()) {
                    case "Yes":
                        role = "Parent";
                        break;

                    case "No":
                        role = "Child";
                        break;
                }

                if(username.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter User Name field", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.isEmpty() || !confirmNewPassword.isEmpty()) {
                    if(!newPassword.equals(confirmNewPassword)) {
                        Toast.makeText(getContext(), "Your new passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateUser(username, role, newPassword);
                    return;
                }
                updateUser(username, role);
            }
        });

    }

    public void updateUser(String username, String role) {
        if (user != null) {
            // Other attributes than "email" will remain unchanged!
            user.setUsername(username);
            user.setRole(role);

            if (photoFile != null) {
                final ParseFile file = new ParseFile(photoFile);
                user.setProfileImage(file);
            }
            // Saves the object.
            user.saveInBackground(e -> {
                if (e == null) {
                    //Save successfull
                    Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Something went wrong while saving
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                goProfileFragment();
            });
        }
    }

    public void updateUser(String username, String role, String newPassword) {
        if (user != null) {
            // Other attributes than "email" will remain unchanged!
            user.setUsername(username);
            user.setRole(role);
            user.setPassword(newPassword);

            if(photoFile!=null) {
                final ParseFile file = new ParseFile(photoFile);
                user.setProfileImage(file);
            }
            // Saves the object.
            user.saveInBackground(e -> {
                if (e == null) {
                    //Save successfull
                    Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Something went wrong while saving
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                goProfileFragment();
            });
        }
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
                        .centerCrop()
                        .circleCrop()
                        .into(ivNewPicture);

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

    private void goProfileFragment() {
        getParentFragmentManager().popBackStack();
    }
}