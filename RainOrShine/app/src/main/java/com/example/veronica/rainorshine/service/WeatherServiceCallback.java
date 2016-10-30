package com.example.veronica.rainorshine.service;

import com.example.veronica.rainorshine.data.Channel;

/**
 * Created by Brandon on 10/29/2016.
 */

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
