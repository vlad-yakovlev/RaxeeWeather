package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.CurrentWeatherFragment;
import com.raxee.raxeeweather.fragment.ForecastWeatherFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
        final ForecastWeatherFragment forecastWeatherFragment = new ForecastWeatherFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_current_weather, currentWeatherFragment)
                .replace(R.id.layout_forecast_weather, forecastWeatherFragment)
                .commit();

        String city = "Ростов-на-Дону";
        currentWeatherFragment.loadWeather(city);
        forecastWeatherFragment.loadWeather(city);
    }
}
