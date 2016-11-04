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
import android.widget.ImageView;
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
    ImageView banner;
    ImageView icon;

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
        banner = (ImageView) getActivity().findViewById(R.id.weatherBanner);
        icon = (ImageView) getActivity().findViewById(R.id.icon);
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

        Toast.makeText(getActivity(), item.getCondition().getDescription(), Toast.LENGTH_SHORT).show();

        weatherDetail.setText(" | "+ ((item.getCondition().getTemperature() - 32) * 5/9)+"°C");
        displayText.setText("Your fashion grid currently displays outfits suitable for " + ((item.getCondition().getTemperature() - 32) * 5/9)+"°C, " + item.getCondition().getDescription() + ".");

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

            banner.setImageResource(R.drawable.rain);
            icon.setImageResource(R.drawable.rain_icon);
        }

        //sunny
        if ( item.getCondition().getDescription().equals("Sunny") ||
                item.getCondition().getDescription().equals("Fair (Day)") ||
                item.getCondition().getDescription().equals("Hot") ||
                item.getCondition().getDescription().equals("Haze") ||
                item.getCondition().getDescription().equals("Mostly Clear") ||
                item.getCondition().getDescription().equals("Clear")) {

            banner.setImageResource(R.drawable.sunny);
            icon.setImageResource(R.drawable.sunny_icon);

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

            banner.setImageResource(R.drawable.snow);
            icon.setImageResource(R.drawable.snow_icon);

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

            banner.setImageResource(R.drawable.cloudy);
            icon.setImageResource(R.drawable.cloudy_icon);

        }

    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }



}
