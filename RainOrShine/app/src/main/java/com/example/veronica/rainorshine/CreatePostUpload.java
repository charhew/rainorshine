package com.example.veronica.rainorshine;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.veronica.rainorshine.data.Channel;
import com.example.veronica.rainorshine.data.Item;
import com.example.veronica.rainorshine.service.WeatherServiceCallback;
import com.example.veronica.rainorshine.service.YahooWeatherService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Charlene on 2016-11-02.
 */

public class CreatePostUpload extends AppCompatActivity implements NumberPicker.OnValueChangeListener, Spinner.OnItemSelectedListener, WeatherServiceCallback {
    ImageView incomingImage;

    byte[] byteImage;
    Uri imageUri;

    ListView dataList;
    EditText captionEditText;
    NumberPicker picker;
    Spinner spinner;

    String caption;
    String weatherCondition;
    int weatherTemp;
    int currentWeatherTemp;

    ArrayList<CameraInput> imageArry = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;
    CameraInput input;

    ImageDatabaseHelper db;

    private YahooWeatherService service;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_upload);

        db = new ImageDatabaseHelper(this);

        incomingImage = (ImageView) findViewById(R.id.incomingImage);

        captionEditText = (EditText) findViewById(R.id.captionEditText);
        picker = (NumberPicker) findViewById(R.id.picker);

        service = new YahooWeatherService(this);
        service.refreshWeather("Vancouver, BC");

        picker.setOnValueChangedListener(this);
        picker.setMinValue(0); // you have to set min and max or else it doesn't work
        picker.setMaxValue(50);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    incomingImage.setImageURI(imageUri);
                }

            }
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weather_condition_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void submitPost(View v) {

        caption = captionEditText.getText().toString();

        //convert the uri to a bitmap
        Bitmap myBitmap = null;
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //convert bitmap to byte. store it in the global byteImage variable.
        if (myBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteImage = stream.toByteArray();
        }


        CameraInput newUploadedImageToBeAdded = new CameraInput(byteImage, caption, picker.getValue(), weatherCondition);
        db.addCameraInput(newUploadedImageToBeAdded);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();


//        caption = captionEditText.getText().toString();
//
//        //convert the uri to a bitmap
//        Bitmap myBitmap = null;
//        try {
//            myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//            Toast.makeText(this, "img converted to bitmap", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //convert bitmap to byte. store it in the global byteImage variable.
//        if (myBitmap != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byteImage = stream.toByteArray();
//            Toast.makeText(this, "bitmap converted to byte[]", Toast.LENGTH_SHORT).show();
//        }
//
//        //convert byte[] to CameraInput
//        CameraInput camInput = new CameraInput(byteImage, "", 0, "");
//
//        Toast.makeText(this, "byte converted to CameraInput", Toast.LENGTH_SHORT).show();
//
//        //add image to database
//        db.addCameraInput(new CameraInput(byteImage, "", 0, ""));

//        int id = input.getID(); // I get the ID of the current row
//
//        ContentValues cv = new ContentValues();
//        cv.put("caption", caption);
//        cv.put("weatherTemp", picker.getValue());
//        cv.put("weatherCondition", weatherCondition);
//
//        db.getWritableDatabase().update("CAMERAINPUTTABLE", cv, "_id=" + id, null);
//        Toast.makeText(this, "image added to database", Toast.LENGTH_SHORT).show();
//
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//        this.finish();

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        weatherTemp = picker.getValue();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        weatherCondition = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void serviceSuccess(Channel channel) {

        Item item = channel.getItem();
        currentWeatherTemp = (item.getCondition().getTemperature() - 32) * 5 / 9;
        picker.setValue(currentWeatherTemp);

    }

    @Override
    public void serviceFailure(Exception exception) {

    }

}
