package com.example.gamezone.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ROLE = "Role";
    public static final String KEY_PROFILE_PICTURE = "profilePicture";

    public User(){
        super();
    }

    public String getKeyUsername(){
        return getString(KEY_USERNAME);
    }

    public String getRole() {
        return getString(KEY_ROLE);
    }

    public void setRole(String role) {
        put(KEY_ROLE, role);
    }

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_PICTURE);
    }

    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_PICTURE, image);
    }

}
