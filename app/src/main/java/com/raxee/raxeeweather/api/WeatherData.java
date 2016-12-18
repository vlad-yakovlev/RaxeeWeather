package com.raxee.raxeeweather.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class WeatherData {
    public Date datetime;
    public Integer temperature;
    public Integer pressure;
    public Integer humidity;
    public String icon;

    public WeatherData() {}

    public WeatherData(JSONObject data) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(data.getLong("dt") * 1000L);
            datetime = cal.getTime();

            JSONObject dataMain = data.getJSONObject("main");
            temperature = (int)(dataMain.getDouble("temp"));
            pressure = (int)(dataMain.getDouble("pressure") * 0.75);
            humidity = (int)(dataMain.getDouble("humidity"));

            JSONObject dataWeather = data.getJSONArray("weather").getJSONObject(0);
            icon = dataWeather.getString("icon");
        } catch (JSONException error) {
            error.printStackTrace();
        }
    }

    public static WeatherData[] buildArray(JSONObject data) {
        WeatherData[] result = null;

        try {
            JSONArray list = data.getJSONArray("list");
            result = new WeatherData[list.length()];
            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                result[i] = new WeatherData(item);
            }
        } catch (JSONException error) {
            error.printStackTrace();
        }

        return result;
    }
}
