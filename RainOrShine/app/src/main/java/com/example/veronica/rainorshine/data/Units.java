package com.example.veronica.rainorshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brandon on 10/29/2016.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {

        return temperature;
    }


    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }

    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("temperature", temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
