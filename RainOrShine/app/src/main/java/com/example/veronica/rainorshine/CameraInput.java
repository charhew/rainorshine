package com.example.veronica.rainorshine;

/**
 * Created by Veronica on 2016-10-30.
 */

public class CameraInput {
    int _id;
    byte[] image;
    String caption;
    String weatherCondition;
    int weatherTemp;

    // Empty constructor
    public CameraInput() {

    }

    // constructor
    public CameraInput(int keyId, byte[] image, String caption, int weatherTemp, String weatherCondition) {
        this._id = keyId;
        this.image = image;
        this.caption = caption;
        this.weatherTemp = weatherTemp;
        this.weatherCondition = weatherCondition;
    }

    public CameraInput(byte[] image, String caption, int weatherTemp, String weatherCondition) {
        this.caption = caption;
        this.image = image;
        this.weatherTemp = weatherTemp;
        this.weatherCondition = weatherCondition;
    }

    public CameraInput(int keyId) {
        this._id = keyId;
    }
    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting caption
    public String getCaption() {
        return this.caption;
    }

    // setting caption
    public void setCaption(String caption) {
        this.caption = caption;
    }

    // weather condition
    public String getWeatherCondition() {
        return this.weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    // weather temp
    public int getWeatherTemp() {
        return this.weatherTemp;
    }

    public void setWeatherTemp(int weatherTemp) {
        this.weatherTemp = weatherTemp;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
