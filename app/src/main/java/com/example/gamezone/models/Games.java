package com.example.gamezone.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Games {

    String backgroundImage;
    String name;
    int id;
    double rating;


    public Games(JSONObject jsonObject) throws JSONException {
        backgroundImage = jsonObject.getString("background_image");
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        rating = jsonObject.getDouble("rating");
    }

    public static List<Games> fromJsonArray(JSONArray resentsJsonArray) throws JSONException {
        List<Games> resents = new ArrayList<>();
        for(int i = 0; i < resentsJsonArray.length(); i++) {
            resents.add(new Games(resentsJsonArray.getJSONObject(i)));
        }
        return resents;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}
