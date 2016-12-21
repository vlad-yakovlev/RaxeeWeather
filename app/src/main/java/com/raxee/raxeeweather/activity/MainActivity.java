package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.WeatherFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WeatherFragment weatherFragment = new WeatherFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_weather, weatherFragment)
                .commit();

        weatherFragment.loadWeather("Ростов-на-Дону");
    }
}
