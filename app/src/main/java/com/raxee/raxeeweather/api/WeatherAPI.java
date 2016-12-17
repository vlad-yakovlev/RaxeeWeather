package com.raxee.raxeeweather.api;

import android.net.Uri;
import android.os.AsyncTask;

import com.raxee.raxeeweather.module.HTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI {
    private static WeatherAPI instance;
    private WeatherAPIInterface weatherAPIInterface;

    public interface WeatherAPIInterface {
        void onGetCurrentWeatherPost(WeatherData current);
        void onGetForecastWeatherPost(WeatherData[] forecast);
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

    public void getCurrentWeather(Object object, String city) {
        weatherAPIInterface = (WeatherAPIInterface)object;

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

        new Task().execute("current", urlCurrentWeather);
    }

    public void getForecastWeather(Object object, String city) {
        weatherAPIInterface = (WeatherAPIInterface)object;

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

        new Task().execute("forecast", urlForecastWeather);
    }

    private class Task extends AsyncTask<String, Void, String[]> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String[] doInBackground(String... params) {
            String[] result = new String[2];
            result[0] = params[0];
            result[1] = HTTP.load(params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            try {
                if (result[1] != null) {
                    switch (result[0]) {
                        case "current":
                            JSONObject currentRaw = new JSONObject(result[1]);
                            WeatherData current = new WeatherData(currentRaw);
                            weatherAPIInterface.onGetCurrentWeatherPost(current);
                            break;

                        case "forecast":
                            JSONObject forecastRaw = new JSONObject(result[1]);
                            JSONArray forecastRawList = forecastRaw.getJSONArray("list");
                            WeatherData[] forecast = new WeatherData[forecastRawList.length()];
                            for (int i = 0; i < forecastRawList.length(); i++) {
                                JSONObject forecastRawListItem = forecastRawList.getJSONObject(i);
                                forecast[i] = new WeatherData(forecastRawListItem);
                            }
                            weatherAPIInterface.onGetForecastWeatherPost(forecast);
                            break;
                    }
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }
    }
}
