package com.example.gamezone.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Stores {

    public String store_name;
    public String store_url;

    public Stores(JSONObject jsonObject, JSONObject nameObject) throws JSONException {
        store_url = jsonObject.getString("url");
        store_name = nameObject.getJSONObject("store").getString("name");
    }

    public static List<Stores> fromJsonArray(JSONArray urlJsonArray, JSONArray nameJsonArray) throws JSONException {
        List<Stores> list = new ArrayList<>();
        for(int i = 0; i < urlJsonArray.length(); i++) {
            list.add(new Stores(urlJsonArray.getJSONObject(i), nameJsonArray.getJSONObject(i)));
        }
        return list;
    }

    public String getStoreName() {
        return store_name;
    }

    public String getLink() {
        return store_url;
    }
}
