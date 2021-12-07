package com.example.gamezone.models;

import android.util.Log;

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
            Log.d("Store", urlJsonArray.getJSONObject(i).getString("url"));

            for(int e = 0; e < nameJsonArray.length(); e++) {
                if(nameJsonArray.getJSONObject(e).getJSONObject("store").getInt("id") == urlJsonArray.getJSONObject(i).getInt("store_id")) {
                    list.add(new Stores(urlJsonArray.getJSONObject(i), nameJsonArray.getJSONObject(e)));
                }
            }
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
