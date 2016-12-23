package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.WeatherFragment;
import com.raxee.raxeeweather.manager.WeatherManager;
import com.raxee.raxeeweather.model.WeatherModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WeatherFragment weatherFragment = new WeatherFragment();

        final String city = "Ростов-на-Дону";

        Bundle arguments = new Bundle();
        arguments.putString("city", city);
        weatherFragment.setArguments(arguments);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_weather, weatherFragment)
                .commit();
    }
}
