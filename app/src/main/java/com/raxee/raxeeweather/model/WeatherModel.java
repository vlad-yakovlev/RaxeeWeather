package com.raxee.raxeeweather.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.raxee.raxeeweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Table(name = "Weather")
public class WeatherModel extends Model implements Serializable {
    @Column(name = "datetime")
    public Date datetime;

    @Column(name = "temperature")
    public Integer temperature;

    @Column(name = "pressure")
    public Integer pressure;

    @Column(name = "humidity")
    public Integer humidity;

    @Column(name = "windSpeed")
    public Integer windSpeed;

    @Column(name = "windDirection")
    public String windDirection;

    @Column(name = "icon")
    public String icon;

    @Column(name = "feelTemperature")
    public Integer feelTemperature;

    @Column(name = "city")
    public String city;

    @Column(name = "type")
    public String type;


    public WeatherModel() {
        super();
    }

    public WeatherModel(JSONObject data, String city, String type) {
        super();

        try {
            datetime = getDatetimeByTimpstamp(data.getLong("dt"));

            JSONObject dataMain = data.getJSONObject("main");
            temperature = (int)Math.round(dataMain.getDouble("temp"));
            pressure = (int)Math.round(dataMain.getDouble("pressure") * 0.75);
            humidity = (int)Math.round(dataMain.getDouble("humidity"));

            JSONObject dataWind = data.getJSONObject("wind");
            windSpeed = (int)Math.round(dataWind.getDouble("speed"));
            windDirection = dataWind.has("deg") ? getWindDirectionByDegree(dataWind.getDouble("deg")) : "";

            JSONObject dataWeather = data.getJSONArray("weather").getJSONObject(0);
            icon = getIcon(dataWeather.getString("icon"));

            feelTemperature = (int)Math.round(getFeelTemperature(dataMain.getDouble("temp"), dataMain.getDouble("humidity"), dataMain.getDouble("pressure"), dataWind.getDouble("speed")));
        } catch (JSONException error) {
            error.printStackTrace();
        }

        this.city = city;
        this.type = type;
    }

    private Date getDatetimeByTimpstamp(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        return cal.getTime();
    }

    private String getWindDirectionByDegree(Double degree) {
        String[] directions = { "{fa-arrow-up}", "{fa-arrow-right}", "{fa-arrow-down}", "{fa-arrow-left}" };
        return directions[(int)(Math.round(degree / 4) % 4)];
    }

    private String getIcon(String id) {
        switch (id) {
            case "01d": return "{wi_day_sunny}";
            case "01n": return "{wi_stars}";

            case "02d": return "{wi_day_cloudy}";
            case "02n": return "{wi_night_alt_cloudy}";

            case "03d": return "{wi_cloud}";
            case "03n": return "{wi_cloud}";

            case "04d": return "{wi_cloudy}";
            case "04n": return "{wi_cloudy}";

            case "09d": return "{wi_rain}";
            case "09n": return "{wi_rain}";

            case "10d": return "{wi_day_rain}";
            case "10n": return "{wi_night_alt_rain}";

            case "11d": return "{wi_thunderstorm}";
            case "11n": return "{wi_thunderstorm}";

            case "13d": return "{wi_snow}";
            case "13n": return "{wi_snow}";

            case "50d": return "{wi_fog}";
            case "50n": return "{wi_fog}";

            default: return null;
        }
    }

    public Double getFeelTemperature(Double temperature, Double humidity, Double pressure, Double wind) {
        Double vaporPressure = (1.0016 + 3.15 * 1e-6 * pressure - 0.074 / pressure) * (6.112 * Math.exp((17.62 * temperature) / (243.12 + temperature)));
        return -2.7 + 1.04 * temperature + 2.0 * vaporPressure * humidity / 100 - 0.65 * wind;
    }

    public static WeatherModel[] buildArray(JSONObject data, String city, String type) {
        WeatherModel[] result = null;

        try {
            JSONArray list = data.getJSONArray("list");
            result = new WeatherModel[list.length()];
            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                result[i] = new WeatherModel(item, city, type);
            }
        } catch (JSONException error) {
            error.printStackTrace();
        }

        return result;
    }
}
