package com.raxee.raxeeweather.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class WeatherData {
    public Date date;
    public Double temp;
    public Double pressure;
    public Double humidity;
    public String weather;
    // TODO: add more

    public WeatherData() {}

    public WeatherData(JSONObject data) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(data.getLong("dt") * 1000L);
            date = cal.getTime();
            JSONObject dataMain = data.getJSONObject("main");
            temp = dataMain.getDouble("temp");
            pressure = dataMain.getDouble("pressure") * 0.75;
            humidity = dataMain.getDouble("humidity");
            weather = data.getJSONArray("weather").getJSONObject(0).getString("description");
        } catch (JSONException error) {
            error.printStackTrace();
        }
    }
}
