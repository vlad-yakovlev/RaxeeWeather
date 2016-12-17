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
    private WeatherData data;

    private TextView dateView;
    private TextView tempView;
    private TextView pressureView;
    private TextView humidityView;
    private TextView weatherView;

    public CurrentWeatherFragment() {}

    public void onGetCurrentWeatherPost(WeatherData current) {
        dateView.setText(String.valueOf(current.date));
        tempView.setText(String.valueOf(current.temp) + " °C");
        pressureView.setText(String.valueOf(current.pressure) + " мм.рт.ст.");
        humidityView.setText(String.valueOf(current.humidity) + " %");
        weatherView.setText(current.weather);
    }

    // TODO: delete
    public void onGetForecastWeatherPost(WeatherData[] forecast) {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        dateView = (TextView)view.findViewById(R.id.date);
        tempView = (TextView)view.findViewById(R.id.temp);
        pressureView = (TextView)view.findViewById(R.id.pressure);
        humidityView = (TextView)view.findViewById(R.id.humidity);
        weatherView = (TextView)view.findViewById(R.id.weather);

        WeatherAPI.getInstance().getCurrentWeather(this, "Ростов-на-Дону");
        return view;
    }
}
