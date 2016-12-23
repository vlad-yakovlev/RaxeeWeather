package com.raxee.raxeeweather.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.manager.WeatherManager;
import com.raxee.raxeeweather.model.WeatherModel;
import com.raxee.raxeeweather.module.DayList;
import com.raxee.raxeeweather.module.FontManager;
import com.raxee.raxeeweather.module.ViewList;

import java.util.Locale;

public class WeatherFragment extends Fragment {
    private String city;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout contentLayout;
    private CurrentLayout currentLayout;
    private ForecastLayout forecastLayout;

    private class CurrentLayout {
        public TextView temperature;
        public TextView icon;
        public ListView list;

        public CurrentLayout(View view) {
            CardView layout = (CardView)view.findViewById(R.id.current_layout);
            temperature = (TextView)layout.findViewById(R.id.temperature);
            icon = (TextView)layout.findViewById(R.id.icon);
            list = (ListView)layout.findViewById(R.id.list);
        }
    }

    private class ForecastLayout {
        public ListView list;

        public ForecastLayout(View view) {
            CardView layout = (CardView)view.findViewById(R.id.forecast_layout);
            list = (ListView)layout.findViewById(R.id.day_list);
        }
    }

    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWeather();
            }
        });

        contentLayout = (LinearLayout)view.findViewById(R.id.content);

        currentLayout = new CurrentLayout(view);
        forecastLayout = new ForecastLayout(view);

        Bundle arguments = this.getArguments();
        city = arguments.getString("city", null);

        loadWeather();

        return view;
    }

    public void loadWeather() {
        WeatherManager.get(city, new WeatherManager.OnCallbackListener() {
            public void onCallback(WeatherModel current, WeatherModel[] forecast) {
                currentLayout.temperature.setText(String.format(Locale.getDefault(), "%d°", current.temperature));
                currentLayout.icon.setTypeface(FontManager.getTypeface(getActivity(), FontManager.WEATHERICONS));
                currentLayout.icon.setText(getResources().getString(current.iconResource));

                ViewList.Item[] weatherList = {
                        new ViewList.Item("Влажность вохдуха", String.format(Locale.getDefault(), "%d %%", current.humidity)),
                        new ViewList.Item("Атмосферное давление", String.format(Locale.getDefault(), "%d мм рт.ст.", current.pressure)),
                        new ViewList.Item("Ветер", String.format(Locale.getDefault(), "%d м/с %s", current.windSpeed, current.windDirection)),
                        new ViewList.Item("Ощущаемая температура", String.format(Locale.getDefault(), "%d °C", current.feelTemperature)),
                };
                ViewList.draw(getActivity(), currentLayout.list, R.layout.layout_list_item, weatherList);

                DayList.draw(getActivity(), forecastLayout.list, R.layout.layout_forecast_entry, forecast);

                contentLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
