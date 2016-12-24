package com.raxee.raxeeweather.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.manager.WeatherManager;
import com.raxee.raxeeweather.model.WeatherModel;
import com.raxee.raxeeweather.list.ForecastList;
import com.raxee.raxeeweather.list.CurrentList;

import java.util.Locale;

public class WeatherFragment extends Fragment {
    private String city;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout contentLayout;
    private TextView currentTemperature;
    private TextView currentIcon;
    private CurrentList currentList;
    private ForecastList forecastList;

    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WeatherManager.load(city, new WeatherManager.OnCallbackListener() {
                    public void onCallback(WeatherModel current, WeatherModel[] forecast) {}
                });
                loadWeather();
            }
        });

        contentLayout = (LinearLayout)view.findViewById(R.id.content);

        CardView currentLayout = (CardView)view.findViewById(R.id.current_layout);
        currentTemperature = (TextView)currentLayout.findViewById(R.id.temperature);
        currentIcon = (TextView)currentLayout.findViewById(R.id.icon);

        currentList = new CurrentList(getActivity(), (ListView)view.findViewById(R.id.current_list), R.layout.entry_current);
        forecastList = new ForecastList(getActivity(), (ListView)view.findViewById(R.id.forecast_list), R.layout.entry_forecast);

        Bundle arguments = this.getArguments();
        city = arguments.getString("city", null);

        loadWeather();

        return view;
    }

    public void loadWeather() {
        WeatherManager.get(city, new WeatherManager.OnCallbackListener() {
            public void onCallback(WeatherModel current, WeatherModel[] forecast) {
                currentTemperature.setText(String.format(Locale.getDefault(), "%d°", current.temperature));
                currentIcon.setText(current.icon);

                CurrentList.Item[] weatherList = {
                        new CurrentList.Item("Влажность вохдуха", String.format(Locale.getDefault(), "%d %%", current.humidity)),
                        new CurrentList.Item("Атмосферное давление", String.format(Locale.getDefault(), "%d мм рт.ст.", current.pressure)),
                        new CurrentList.Item("Ветер", String.format(Locale.getDefault(), "%d м/с %s", current.windSpeed, current.windDirection)),
                        new CurrentList.Item("Ощущаемая температура", String.format(Locale.getDefault(), "%d °C", current.feelTemperature)),
                };
                currentList.set(weatherList);

                forecastList.set(forecast);

                contentLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
