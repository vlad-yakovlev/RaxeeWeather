package com.raxee.raxeeweather.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.api.WeatherAPI;
import com.raxee.raxeeweather.api.WeatherData;

public class CurrentWeatherFragment extends Fragment {
    private LinearLayout layoutView;
    private TextView temperatureView;
    private TextView pressureView;
    private TextView humidityView;

    public CurrentWeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        layoutView = (LinearLayout)view.findViewById(R.id.layout);

        temperatureView = (TextView)view.findViewById(R.id.temperature);
        pressureView = (TextView)view.findViewById(R.id.pressure);
        humidityView = (TextView)view.findViewById(R.id.humidity);

        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getInstance().getCurrentWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] current) {
                WeatherData weather = current[0];

                temperatureView.setText(weather.temperature.toString() + "°");
                pressureView.setText(weather.pressure.toString() + " мм рт.ст.");
                humidityView.setText(weather.humidity.toString() + "%");

                layoutView.setVisibility(View.VISIBLE);
            }
        });
    }
}
