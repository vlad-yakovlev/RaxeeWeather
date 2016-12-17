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
    private TextView dateView;
    private TextView tempView;
    private TextView pressureView;
    private TextView humidityView;
    private TextView weatherView;

    public CurrentWeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        layoutView = (LinearLayout)view.findViewById(R.id.layout);
        dateView = (TextView)view.findViewById(R.id.date);
        tempView = (TextView)view.findViewById(R.id.temp);
        pressureView = (TextView)view.findViewById(R.id.pressure);
        humidityView = (TextView)view.findViewById(R.id.humidity);
        weatherView = (TextView)view.findViewById(R.id.weather);

        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getInstance().getCurrentWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] current) {
                dateView.setText(String.valueOf(current[0].date));
                tempView.setText(String.valueOf(current[0].temp) + " °C");
                pressureView.setText(String.valueOf(current[0].pressure) + " мм.рт.ст.");
                humidityView.setText(String.valueOf(current[0].humidity) + " %");
                weatherView.setText(current[0].weather);
                layoutView.setVisibility(View.VISIBLE);
            }
        });
    }
}
