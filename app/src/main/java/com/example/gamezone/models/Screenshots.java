package com.example.gamezone.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Screenshots {

    public String screenshot;

    public Screenshots(JSONObject jsonObject) throws JSONException {
        screenshot = jsonObject.getString("image");
    }

    public static List<Screenshots> fromJsonArray(JSONArray resentsJsonArray) throws JSONException {
        List<Screenshots> list = new ArrayList<>();
        for(int i = 0; i < resentsJsonArray.length(); i++) {
            list.add(new Screenshots(resentsJsonArray.getJSONObject(i)));
        }
        return list;
    }

    public String getScreenshot() {
        return screenshot;
    }
}
