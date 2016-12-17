package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.CurrentWeatherFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_current_weather, new CurrentWeatherFragment())
                .commit();
    }
}
