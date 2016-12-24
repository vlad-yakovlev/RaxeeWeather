package com.raxee.raxeeweather.application;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
