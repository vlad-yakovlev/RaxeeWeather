package com.raxee.raxeeweather.api;

import android.net.Uri;
import android.os.AsyncTask;

import com.raxee.raxeeweather.module.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI {
    private static WeatherAPI instance;

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

    public interface OnCallbackListener {
        void onCallback(WeatherData[] data);
    }

    public void getCurrentWeather(String city, OnCallbackListener onCallbackListener) {
        new Task("current", onCallbackListener).execute(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=df27b084e5286716dee61c9f45f82a1a", city));
    }

    public void getForecastWeather(String city, OnCallbackListener onCallbackListener) {
        new Task("forecast", onCallbackListener).execute(String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=df27b084e5286716dee61c9f45f82a1a", city));
    }

    private class Task extends AsyncTask<String, Void, String> {
        String type;
        OnCallbackListener onCallbackListener;

        public Task(String type, OnCallbackListener onCallbackListener) {
            super();
            this.type = type;
            this.onCallbackListener = onCallbackListener;
        }

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
                switch (type) {
                    case "current":
                        WeatherData[] current = { new WeatherData(new JSONObject(result)) };
                        onCallbackListener.onCallback(current);
                        break;

                    case "forecast":
                        WeatherData[] forecast = WeatherData.buildArray(new JSONObject(result));
                        onCallbackListener.onCallback(forecast);
                        break;
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }
    }
}
