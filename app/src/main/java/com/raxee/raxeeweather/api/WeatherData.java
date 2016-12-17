package com.raxee.raxeeweather.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class WeatherData {
    public Date date;
    public Double temp;
    public Double minTemp;
    public Double maxTemp;
    public Double pressure;
    public Double humidity;
    public String weather;
    // Add more

    public WeatherData() {}

    public WeatherData(JSONObject data) {
        try {
            date = new Date(data.getInt("dt"));
            JSONObject dataMain = data.getJSONObject("main");
            temp = dataMain.getDouble("temp");
            minTemp = dataMain.getDouble("temp_min");
            maxTemp = dataMain.getDouble("temp_max");
            pressure = dataMain.getDouble("pressure");
            humidity = dataMain.getDouble("humidity");
            weather = data.getJSONArray("weather").getJSONObject(0).getString("description");
        } catch (JSONException error) {
            error.printStackTrace();
        }
    }
}
