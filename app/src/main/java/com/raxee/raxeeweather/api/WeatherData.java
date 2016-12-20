package com.raxee.raxeeweather.api;

import com.raxee.raxeeweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.round;

public class WeatherData {
    public Date datetime;
    public Integer temperature;
    public Integer pressure;
    public Integer humidity;
    public Integer windSpeed;
    public String windDirection;
    public Integer icon;

    public WeatherData() {}

    public WeatherData(JSONObject data) {
        try {
            datetime = getDatetimeByTimpstamp(data.getLong("dt"));

            JSONObject dataMain = data.getJSONObject("main");
            temperature = (int)round(dataMain.getDouble("temp"));
            pressure = (int)round(dataMain.getDouble("pressure") * 0.75);
            humidity = (int)round(dataMain.getDouble("humidity"));

            JSONObject dataWind = data.getJSONObject("wind");
            windSpeed = (int)round(dataWind.getDouble("speed"));
            windDirection = getWindDirectionByDegree(dataWind.getDouble("deg"));

            JSONObject dataWeather = data.getJSONArray("weather").getJSONObject(0);
            icon = getIconByID(dataWeather.getString("icon"));
        } catch (JSONException error) {
            error.printStackTrace();
        }
    }

    private Date getDatetimeByTimpstamp(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        return cal.getTime();
    }

    private String getWindDirectionByDegree(Double degree) {
        String[] directions = { "↑", "↗", "→", "↘", "↓", "↙", "←", "↖" };
        return directions[(int)(round(degree / 8) % 8)];
    }

    private Integer getIconByID(String id) {
        switch (id) {
            case "01d": return R.drawable.day_sunny;
            case "01n": return R.drawable.stars;

            case "02d": return R.drawable.day_cloudy;
            case "02n": return R.drawable.night_alt_cloudy;

            case "03d": return R.drawable.cloud;
            case "03n": return R.drawable.cloud;

            case "04d": return R.drawable.cloudy;
            case "04n": return R.drawable.cloudy;

            case "09d": return R.drawable.rain;
            case "09n": return R.drawable.rain;

            case "10d": return R.drawable.day_rain;
            case "10n": return R.drawable.night_alt_rain;

            case "11d": return R.drawable.thunderstorm;
            case "11n": return R.drawable.thunderstorm;

            case "13d": return R.drawable.snow;
            case "13n": return R.drawable.snow;

            case "50d": return R.drawable.fog;
            case "50n": return R.drawable.fog;

            default: return null;
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
