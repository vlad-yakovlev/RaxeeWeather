package com.raxee.raxeeweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI {
    private static WeatherAPI instance;
    private WeatherAPIInterface weatherAPIInterface;

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

    public void getWeather(Object object) {
        weatherAPIInterface = (WeatherAPIInterface)object;

        Uri.Builder builderWeatherNow = new Uri.Builder();
        builderWeatherNow
                .scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("lat", "0")
                .appendQueryParameter("lon", "0")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("appid", "df27b084e5286716dee61c9f45f82a1a");
        String urlWeatherNow = builderWeatherNow.build().toString();

        new Task().execute(urlWeatherNow);
    }

    private class Task extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            return HTTP.load(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject data = new JSONObject(result);

                weatherAPIInterface.onWeatherAPIPost(1111);
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }
    }
}
