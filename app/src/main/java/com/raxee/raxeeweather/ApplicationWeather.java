package com.raxee.raxeeweather;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class ApplicationWeather extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
