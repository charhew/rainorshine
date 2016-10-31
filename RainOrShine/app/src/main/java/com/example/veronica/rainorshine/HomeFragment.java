package com.example.veronica.rainorshine;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veronica.rainorshine.data.Channel;
import com.example.veronica.rainorshine.data.Condition;
import com.example.veronica.rainorshine.data.Item;
import com.example.veronica.rainorshine.service.WeatherServiceCallback;
import com.example.veronica.rainorshine.service.YahooWeatherService;

/**
 * Created by Charlene on 2016-10-27.
 */
public class HomeFragment extends Fragment implements WeatherServiceCallback {
    private YahooWeatherService service;
    TextView weatherDetail;
    TextView displayText;
    ImageView banner;

    TextView helloName;
    String firstName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent i = getActivity().getIntent();
        firstName = i.getStringExtra("name");
        Toast.makeText(getActivity(), firstName, Toast.LENGTH_SHORT).show();

        displayText = (TextView) getActivity().findViewById(R.id.displayTextView);
        weatherDetail = (TextView) getActivity().findViewById(R.id.tempTextView);
        banner = (ImageView) getActivity().findViewById(R.id.weatherBanner);
        service = new YahooWeatherService(this);
        service.refreshWeather("Vancouver, BC");

        helloName = (TextView) getActivity().findViewById(R.id.helloTextView);
        helloName.setText("Hello,\n" + firstName + ".");
    }



    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();

        weatherDetail.setText(" "+ ((item.getCondition().getTemperature() - 32) * 5/9)+"°C");
        displayText.setText("Your fashion grid currently displays outfits suitable for " + ((item.getCondition().getTemperature() - 32) * 5/9)+" °C, " + item.getCondition().getDescription() + ".");

        //rain
        if (item.getCondition().getCode() == 1 ||
                item.getCondition().getCode() == 10 ||
                item.getCondition().getCode() == 11 ||
                item.getCondition().getCode() == 12 ||
                item.getCondition().getCode() == 17 ||
                item.getCondition().getCode() == 18 ||
                item.getCondition().getCode() == 35 ||
                item.getCondition().getCode() == 40 ||
                item.getCondition().getCode() == 17 ||
                item.getCondition().getCode() == 9 ) {

            banner.setImageResource(R.drawable.rain);

        }

        //sunny
        if (item.getCondition().getCode() == 32 ||
                item.getCondition().getCode() == 34 ||
                item.getCondition().getCode() == 36 ||
                item.getCondition().getCode() == 21 ) {

            banner.setImageResource(R.drawable.sunny);

        }

        //snow
        if (item.getCondition().getCode() == 5 ||
                item.getCondition().getCode() == 6 ||
                item.getCondition().getCode() == 7 ||
                item.getCondition().getCode() == 8 ||
                item.getCondition().getCode() == 10 ||
                item.getCondition().getCode() == 13 ||
                item.getCondition().getCode() == 14 ||
                item.getCondition().getCode() == 16 ||
                item.getCondition().getCode() == 18 ||
                item.getCondition().getCode() == 41 ||
                item.getCondition().getCode() == 42 ||
                item.getCondition().getCode() == 46) {

            banner.setImageResource(R.drawable.snow);

        }

        //cloudy
        if (item.getCondition().getCode() == 20 ||
                item.getCondition().getCode() == 26 ||
                item.getCondition().getCode() == 27 ||
                item.getCondition().getCode() == 28 ||
                item.getCondition().getCode() == 29 ||
                item.getCondition().getCode() == 30 ||
                item.getCondition().getCode() == 44 ) {

            banner.setImageResource(R.drawable.cloudy);

        }

    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }



}
