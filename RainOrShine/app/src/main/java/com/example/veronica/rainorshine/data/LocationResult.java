package com.example.veronica.rainorshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brandon on 10/29/2016.
 */

public class LocationResult {

    private String address;

    public String getAddress() {
        return address;
    }


    public void populate(JSONObject data) {
        address = data.optString("formatted_address");
    }


    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("formatted_address", address);
        } catch (JSONException e) {}

        return data;
    }
}
