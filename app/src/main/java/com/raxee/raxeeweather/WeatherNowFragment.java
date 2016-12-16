package com.raxee.raxeeweather;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WeatherNowFragment extends Fragment implements WeatherAPI.WeatherAPIInterface {
    TextView temperatureView;

    public WeatherNowFragment() {}

    public void onWeatherAPIPost(double temperature) {
        temperatureView.setText(String.valueOf(temperature));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_now, container, false);
        temperatureView = (TextView)view.findViewById(R.id.temperature);
        WeatherAPI.getInstance().getWeather(this);
        return view;
    }
}
