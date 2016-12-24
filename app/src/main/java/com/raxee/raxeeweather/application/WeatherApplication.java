package com.raxee.raxeeweather.application;

import com.activeandroid.ActiveAndroid;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.raxee.raxeeweather.model.CityModel;
import com.raxee.raxeeweather.module.Keyboard;

import android.app.Application;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        Iconify
                .with(new FontAwesomeModule())
                .with(new WeathericonsModule());
    }
}
