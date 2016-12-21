package com.raxee.raxeeweather.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.api.WeatherAPI;
import com.raxee.raxeeweather.api.WeatherData;
import com.raxee.raxeeweather.module.DayList;
import com.raxee.raxeeweather.module.FontManager;
import com.raxee.raxeeweather.module.List;

import java.util.Locale;

public class WeatherFragment extends Fragment {
    private class CurrentLayout {
        public CardView layout;
        public TextView temperature;
        public TextView icon;
        public ListView list;

        public CurrentLayout(View view) {
            layout = (CardView)view.findViewById(R.id.current_layout);
            temperature = (TextView)layout.findViewById(R.id.temperature);
            icon = (TextView)layout.findViewById(R.id.icon);
            list = (ListView)layout.findViewById(R.id.list);
        }
    }

    private class ForecastLayout {
        public CardView layout;
        public ListView list;

        public ForecastLayout(View view) {
            layout = (CardView)view.findViewById(R.id.forecast_layout);
            list = (ListView)layout.findViewById(R.id.day_list);
        }
    }

    CurrentLayout currentLayout;
    ForecastLayout forecastLayout;

    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        currentLayout = new CurrentLayout(view);
        forecastLayout = new ForecastLayout(view);
        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getCurrentWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] current) {
                WeatherData weather = current[0];

                currentLayout.temperature.setText(String.format(Locale.getDefault(), "%d°", weather.temperature));
                currentLayout.icon.setTypeface(FontManager.getTypeface(getActivity(), FontManager.WEATHERICONS));
                currentLayout.icon.setText(getResources().getString(weather.iconResource));

                List.Item[] weatherList = {
                        new List.Item("Влажность вохдуха", String.format(Locale.getDefault(), "%d %%", weather.humidity)),
                        new List.Item("Атмосферное давление", String.format(Locale.getDefault(), "%d мм рт.ст.", weather.pressure)),
                        new List.Item("Ветер", String.format(Locale.getDefault(), "%d м/с %s", weather.windSpeed, weather.windDirection)),
                        new List.Item("Ощущаемая температура", String.format(Locale.getDefault(), "%d °C", weather.feelTemperature)),
                };
                List.draw(getActivity(), currentLayout.list, R.layout.layout_list_item, weatherList);

                currentLayout.layout.setVisibility(View.VISIBLE);
            }
        });

        WeatherAPI.getForecastWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] forecast) {
                DayList.draw(getActivity(), forecastLayout.list, R.layout.layout_forecast_entry, forecast);

                forecastLayout.layout.setVisibility(View.VISIBLE);
            }
        });
    }
}
