package com.example.veronica.rainorshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brandon on 10/29/2016.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }


    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        try {
            data.put("condition", condition.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
