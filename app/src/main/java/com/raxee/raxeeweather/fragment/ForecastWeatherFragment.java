package com.raxee.raxeeweather.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.api.WeatherAPI;
import com.raxee.raxeeweather.api.WeatherData;

import java.text.SimpleDateFormat;

public class ForecastWeatherFragment extends Fragment {
    private LinearLayout layoutView;
    private ListView weatherListView;

    public ForecastWeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);

        layoutView = (LinearLayout)view.findViewById(R.id.layout);
        weatherListView = (ListView)view.findViewById(R.id.weather_day_list);

        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getInstance().getForecastWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] forecast) {
                weatherListView.setAdapter(new WeatherListAdapter(getActivity(), R.layout.layout_weather_item_sm, forecast));
                weatherListView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, forecast.length * 49 - 1, getResources().getDisplayMetrics());

                layoutView.setVisibility(View.VISIBLE);
            }
        });
    }

    public class WeatherListAdapter extends ArrayAdapter<WeatherData> {
        private final Context context;
        private final int layout;
        private WeatherData[] data = null;

        public WeatherListAdapter(Context context, int layout, WeatherData[] data) {
            super(context, layout, data);
            this.context = context;
            this.layout = layout;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layout, parent, false);
            WeatherData weather = data[position];

            SimpleDateFormat dateDormat = new SimpleDateFormat("EE HH:mm");

            ((TextView)view.findViewById(R.id.datetime)).setText(dateDormat.format(weather.datetime).toUpperCase());
            ((TextView)view.findViewById(R.id.temperature)).setText(weather.temperature.toString());

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
