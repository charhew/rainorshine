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
        //Set the spinner to the current temperature by default
        Item item = channel.getItem();
        currentWeatherTemp = (item.getCondition().getTemperature() - 32) * 5 / 9;
        picker.setValue(currentWeatherTemp);

        //Set the spinner to the current weather by default
        //rain
        if (item.getCondition().getDescription().equals("Severe Thunderstorms") ||
                item.getCondition().getDescription().equals("Scattered Showers") ||
                item.getCondition().getDescription().equals("Thunderstorms") ||
                item.getCondition().getDescription().equals("Mixed Rain And Snow") ||
                item.getCondition().getDescription().equals("Mixed Rain And Sleet") ||
                item.getCondition().getDescription().equals("Drizzle") ||
                item.getCondition().getDescription().equals("Freezing Rain") ||
                item.getCondition().getDescription().equals("Showers") ||
                item.getCondition().getDescription().equals("Hail") ||
                item.getCondition().getDescription().equals("Rain") ||
                item.getCondition().getDescription().equals("Mixed Rain And Hail") ||
                item.getCondition().getDescription().equals("Thundershowers")){

            spinner.setSelection(3);

        }

        //sunny
        if ( item.getCondition().getDescription().equals("Sunny") ||
                item.getCondition().getDescription().equals("Fair (Day)") ||
                item.getCondition().getDescription().equals("Hot") ||
                item.getCondition().getDescription().equals("Haze") ||
                item.getCondition().getDescription().equals("Mostly Clear") ||
                item.getCondition().getDescription().equals("Clear")) {
            spinner.setSelection(0);
        }

        //clear
        if (item.getCondition().getDescription().equals("Mostly Clear") ||
                item.getCondition().getDescription().equals("Clear")) {
            spinner.setSelection(4);

        }

        //snow
        if (item.getCondition().getDescription().equals("Mixed Rain And Snow") ||
                item.getCondition().getDescription().equals("Mixed Rain And Sleet") ||
                item.getCondition().getDescription().equals("Mixed Snow And Sleet") ||
                item.getCondition().getDescription().equals("Freezing Drizzle") ||
                item.getCondition().getDescription().equals("Freezing Rain") ||
                item.getCondition().getDescription().equals("Snow Flurries") ||
                item.getCondition().getDescription().equals("Light Snow Showers") ||
                item.getCondition().getDescription().equals("Snow") ||
                item.getCondition().getDescription().equals("Sleet") ||
                item.getCondition().getDescription().equals("Heavy Snow") ||
                item.getCondition().getDescription().equals("Scattered Snow Showers") ||
                item.getCondition().getDescription().equals("Snow Showers")) {
            spinner.setSelection(2);

        }

        //cloudy
        if (item.getCondition().getDescription().equals("Foggy") ||
                item.getCondition().getDescription().equals("Cloudy") ||
                item.getCondition().getDescription().equals("Mostly Cloudy (Night)") ||
                item.getCondition().getDescription().equals("Mostly Cloudy (Day)") ||
                item.getCondition().getDescription().equals("Mostly Cloudy") ||
                item.getCondition().getDescription().equals("Partly Cloudy (Day)") ||
                item.getCondition().getDescription().equals("Partly Cloudy (Night)") ||
                item.getCondition().getDescription().equals("Partly Cloudy") ||
                item.getCondition().getDescription().equals("Cloud") ) {
            spinner.setSelection(1);

        }

    }

    @Override
    public void serviceFailure(Exception exception) {

    }

}
