package com.raxee.raxeeweather.api;

import android.net.Uri;
import android.os.AsyncTask;

import com.raxee.raxeeweather.module.HTTP;

import org.json.JSONArray;
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
        void onCallback(WeatherData[] forecast);
    }

    public void getCurrentWeather(String city, OnCallbackListener onCallbackListener) {
        Uri.Builder builderCurrentWeather = new Uri.Builder();
        builderCurrentWeather
                .scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("q", city)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .appendQueryParameter("appid", "df27b084e5286716dee61c9f45f82a1a");
        String urlCurrentWeather = builderCurrentWeather.build().toString();

        new Task("current", onCallbackListener).execute(urlCurrentWeather);
    }

    public void getForecastWeather(String city, OnCallbackListener onCallbackListener) {
        Uri.Builder builderForecastWeather = new Uri.Builder();
        builderForecastWeather
                .scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter("q", city)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("lang", "ru")
                .appendQueryParameter("appid", "df27b084e5286716dee61c9f45f82a1a");
        String urlForecastWeather = builderForecastWeather.build().toString();

        new Task("forecast", onCallbackListener).execute(urlForecastWeather);
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
                        JSONObject currentRaw = new JSONObject(result);
                        WeatherData[] current = new WeatherData[1];
                        current[0] = new WeatherData(currentRaw);
                        onCallbackListener.onCallback(current);
                        break;

                    case "forecast":
                        JSONObject forecastRaw = new JSONObject(result);
                        JSONArray forecastRawList = forecastRaw.getJSONArray("list");
                        WeatherData[] forecast = new WeatherData[forecastRawList.length()];
                        for (int i = 0; i < forecastRawList.length(); i++) {
                            JSONObject forecastRawListItem = forecastRawList.getJSONObject(i);
                            forecast[i] = new WeatherData(forecastRawListItem);
                        }
                        onCallbackListener.onCallback(forecast);
                        break;
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }
    }
}
