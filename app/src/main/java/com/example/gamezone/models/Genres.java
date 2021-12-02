package com.example.gamezone.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Genres {

    String backgroundImage;
    String name;
    int id;

    public Genres(JSONObject jsonObject) throws JSONException {
        backgroundImage = jsonObject.getString("image_background");
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
    }

    public static List<Genres> fromJsonArray(JSONArray resentsJsonArray) throws JSONException {
        List<Genres> genres = new ArrayList<>();
        for(int i = 0; i < resentsJsonArray.length(); i++) {
            genres.add(new Genres(resentsJsonArray.getJSONObject(i)));
        }
        return genres;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
