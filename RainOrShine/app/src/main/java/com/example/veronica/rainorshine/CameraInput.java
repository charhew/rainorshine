package com.example.veronica.rainorshine;

/**
 * Created by Veronica on 2016-10-30.
 */

public class CameraInput {
    int _id;
    byte[] image;
    String caption;

    // Empty constructor
    public CameraInput() {

    }

    // constructor
    public CameraInput(int keyId, byte[] image, String caption) {
        this._id = keyId;
        this.image = image;
        this.caption = caption;
    }

    public CameraInput(String caption, byte[] image) {
        this.caption = caption;
        this.image = image;

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

    // getting name
    public String getCaption() {
        return this.caption;
    }

    // setting name
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
