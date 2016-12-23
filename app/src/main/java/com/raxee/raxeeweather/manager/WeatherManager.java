package com.raxee.raxeeweather.manager;

import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.raxee.raxeeweather.model.WeatherModel;
import com.raxee.raxeeweather.module.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherManager {
    public static interface OnCallbackListener {
        void onCallback(WeatherModel current, WeatherModel[] forecast);
    }

    public static void get(String city, OnCallbackListener onCallbackListener) {
        List<WeatherModel> currentList = new Select()
                .from(WeatherModel.class)
                .where("city = ?", city).and("type = 'current'")
                .execute();
        WeatherModel[] current = currentList.toArray(new WeatherModel[currentList.size()]);

        List<WeatherModel> forecastList = new Select()
                .from(WeatherModel.class)
                .where("city = ?", city).and("type = 'forecast'")
                .execute();
        WeatherModel[] forecast = forecastList.toArray(new WeatherModel[forecastList.size()]);

        if (current.length > 0 && forecast.length > 0) {
            onCallbackListener.onCallback(current[0], forecast);
        } else {
            load(city, onCallbackListener);
        }
    }

    public static void load(String city, final OnCallbackListener onCallbackListener) {
        String currentUrl = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=df27b084e5286716dee61c9f45f82a1a", city);
        String forecastUrl = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=df27b084e5286716dee61c9f45f82a1a", city);
        new Task(city, onCallbackListener).execute(currentUrl, forecastUrl);
    }

    private static class Task extends AsyncTask<String, Void, String[]> {
        private String city;
        private OnCallbackListener onCallbackListener;
        private static String[] types = { "current", "forecast" };

        public Task(String city, OnCallbackListener onCallbackListener) {
            super();
            this.city = city;
            this.onCallbackListener = onCallbackListener;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String[] doInBackground(String... urls) {
            String[] result = new String[urls.length];
            for (Integer i = 0; i < urls.length; i++) {
                result[i] = urls[i];
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] results) {
            super.onPostExecute(results);

            for (String result : results) {
                if (result == null) {
                    return;
                }
            }

            WeatherModel current = null;
            WeatherModel[] forecast = null;

            for (Integer i = 0; i < results.length; i++) {
                try {
                    String type = types[i];
                    String result = results[i];

                    WeatherModel[] weatherModels = null;

                    switch (type) {
                        case "current":
                            weatherModels = new WeatherModel[]{ new WeatherModel(new JSONObject(result), city, type) };
                            break;

                        case "forecast":
                            weatherModels = WeatherModel.buildArray(new JSONObject(result), city, type);
                            break;
                    }

                    new Delete()
                            .from(WeatherModel.class)
                            .where("city = ?", city).and("type = ?", type)
                            .execute();

                    ActiveAndroid.beginTransaction();
                    try {
                        for (WeatherModel weatherModel : weatherModels) {
                            weatherModel.save();
                        }
                        ActiveAndroid.setTransactionSuccessful();
                    } finally {
                        ActiveAndroid.endTransaction();
                    }

                    switch (type) {
                        case "current":
                            current = weatherModels[0];
                            break;

                        case "forecast":
                            forecast = weatherModels;
                            break;
                    }
                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }

            onCallbackListener.onCallback(current, forecast);
        }
    }
}
