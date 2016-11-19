package com.example.veronica.rainorshine;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.veronica.rainorshine.data.Channel;
import com.example.veronica.rainorshine.data.Item;
import com.example.veronica.rainorshine.service.WeatherServiceCallback;
import com.example.veronica.rainorshine.service.YahooWeatherService;

import java.util.ArrayList;

public class CreatePost extends AppCompatActivity implements NumberPicker.OnValueChangeListener, Spinner.OnItemSelectedListener, WeatherServiceCallback {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        dataList = (ListView) findViewById(R.id.list);
        captionEditText = (EditText) findViewById(R.id.captionEditText);
        picker = (NumberPicker) findViewById(R.id.picker);

        service = new YahooWeatherService(this);
        service.refreshWeather("Vancouver, BC");

        picker.setOnValueChangedListener(this);
        picker.setMinValue(0); // you have to set min and max or else it doesn't work
        picker.setMaxValue(50);

        db = new ImageDatabaseHelper(this);

        input = db.getCameraInput();
        imageArry.add(input);

        imageAdapter = new ImageAdapter(this, R.layout.create_post_item,
                imageArry);
        dataList.setAdapter(imageAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weather_condition_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void submitPost(View v) {
        caption = captionEditText.getText().toString();
        int id = input.getID(); // I get the ID of the current row

        ContentValues cv = new ContentValues();
        cv.put("caption", caption);
        cv.put("weatherTemp", picker.getValue());
        cv.put("weatherCondition", weatherCondition);

        db.getWritableDatabase().update("CAMERAINPUTTABLE", cv, "_id=" + id, null);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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
        currentWeatherTemp = (item.getCondition().getTemperature() - 32) * 5/9;
        picker.setValue(currentWeatherTemp);

    }

    @Override
    public void serviceFailure(Exception exception) {

    }
}
