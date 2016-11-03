package com.example.veronica.rainorshine;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veronica.rainorshine.data.Channel;
import com.example.veronica.rainorshine.data.Item;
import com.example.veronica.rainorshine.service.WeatherServiceCallback;
import com.example.veronica.rainorshine.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlene on 2016-10-27.
 */
public class HomeFragment extends Fragment implements WeatherServiceCallback {
    private YahooWeatherService service;
    TextView weatherDetail;
    TextView displayText;

    TextView helloName;
    String firstName;

    GridView dataGrid;
    public ArrayList<CameraInput> cameraInputArray = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;

    ImageDatabaseHelper db;
    public static final String DEFAULT = "not available";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent i = getActivity().getIntent();

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
        firstName = sharedPrefs.getString("name", DEFAULT);

        displayText = (TextView) getActivity().findViewById(R.id.displayTextView);
        weatherDetail = (TextView) getActivity().findViewById(R.id.tempTextView);
        service = new YahooWeatherService(this);
        service.refreshWeather("Vancouver, BC");

        helloName = (TextView) getActivity().findViewById(R.id.helloTextView);
        helloName.setText("Hello,\n" + firstName + ".");

        dataGrid = (GridView) getActivity().findViewById(R.id.gridview);

        db = new ImageDatabaseHelper(getActivity());

        List<CameraInput> inputs = db.getAllCameraInputs();
        for (CameraInput ci : inputs) {
            cameraInputArray.add(ci);
        }

        imageAdapter = new ImageAdapter(getActivity(), R.layout.grid_item, cameraInputArray);
        dataGrid.setAdapter(imageAdapter);

        dataGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), PostDetails.class);
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        });
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();

        weatherDetail.setText(" | "+ ((item.getCondition().getTemperature() - 32) * 5/9)+"°C");
        displayText.setText("Your fashion grid currently displays outfits suitable for " + ((item.getCondition().getTemperature() - 32) * 5/9)+"°C.");

       // Toast.makeText(getActivity(), item.getCondition().getTemperature(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }



}
