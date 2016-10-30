package com.example.veronica.rainorshine.data;

import org.json.JSONObject;

/**
 * Created by Brandon on 10/29/2016.
 */

public interface JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}
