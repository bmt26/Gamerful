package com.example.gamezone.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Reviews")
public class Reviews extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_START_RATING = "starRating";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_GAME = "game";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public int getStarRating() {
        return getInt(KEY_START_RATING);
    }

    public void setStartRating(int startRating) {
        put(KEY_START_RATING, startRating);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public String getComment() {
        return getString(KEY_COMMENT);
    }

    public void setComment(String comment) {
        put(KEY_COMMENT, comment);
    }

    public String getGame() {
        return getString(KEY_GAME);
    }

    public void setGame(String game) {
        put(KEY_GAME, game);
    }

}
