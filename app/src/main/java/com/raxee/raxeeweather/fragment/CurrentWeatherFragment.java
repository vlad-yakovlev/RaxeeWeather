package com.raxee.raxeeweather.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.api.WeatherAPI;
import com.raxee.raxeeweather.api.WeatherData;


public class CurrentWeatherFragment extends Fragment implements WeatherAPI.WeatherAPIInterface {
    TextView dateView;
    TextView tempView;
    TextView tempMinMaxView;
    TextView pressureView;
    TextView humidityView;
    TextView weatherView;

    public CurrentWeatherFragment() {}

    public void onWeatherAPIPost(WeatherData current, WeatherData[] forecast) {
        dateView.setText(String.valueOf(current.date));
        tempMinMaxView.setText(String.valueOf(current.minTemp) + " - " + String.valueOf(current.maxTemp) + " °C");
        tempView.setText(String.valueOf(current.temp) + " °C");
        pressureView.setText(String.valueOf(current.pressure));
        humidityView.setText(String.valueOf(current.humidity) + " %");
        weatherView.setText(current.weather);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        dateView = (TextView)view.findViewById(R.id.date);
        tempView = (TextView)view.findViewById(R.id.temp);
        tempMinMaxView = (TextView)view.findViewById(R.id.temp_min_max);
        pressureView = (TextView)view.findViewById(R.id.pressure);
        humidityView = (TextView)view.findViewById(R.id.humidity);
        weatherView = (TextView)view.findViewById(R.id.weather);

        WeatherAPI.getInstance().getWeather(this, "0", "0");
        return view;
    }
}
