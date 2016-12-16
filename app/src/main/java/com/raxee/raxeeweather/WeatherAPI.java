package com.raxee.raxeeweather;

public class WeatherAPI {
    private static WeatherAPI instance;

    public interface WeatherAPIInterface {
        void onWeatherAPIPost(double temperature);
    }

    private WeatherAPI() {
        instance = this;
    }

    public static WeatherAPI getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new WeatherAPI();
        }
    }

    public void getWeather(Object view) {
        ((WeatherAPIInterface)view).onWeatherAPIPost(4.8);
    }
}
