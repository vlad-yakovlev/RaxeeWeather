package com.raxee.raxeeweather.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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

public class ForecastWeatherFragment extends Fragment {
    private LinearLayout layoutView;
    private ListView weatherListView;

    public ForecastWeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);

        layoutView = (LinearLayout)view.findViewById(R.id.layout);
        weatherListView = (ListView)view.findViewById(R.id.weather_list);

        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getInstance().getForecastWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] forecast) {
                layoutView.setVisibility(View.VISIBLE);
                weatherListView.setAdapter(new ForecastAdapter(getActivity(), R.layout.layout_weather_item, forecast));
            }
        });
    }

    public class ForecastAdapter extends ArrayAdapter<WeatherData> {
        private final Context context;
        private final int layoutResourceId;
        private WeatherData data[] = null;

        public ForecastAdapter(Context context, int layoutResourceId, WeatherData[] data) {
            super(context, layoutResourceId, data);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parentGroup) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layoutResourceId, parentGroup, false);
            WeatherData weather = data[position];

            ((TextView)view.findViewById(R.id.date)).setText(String.valueOf(weather.date));
            ((TextView)view.findViewById(R.id.temp)).setText(String.valueOf(weather.temp) + " °C");
            ((TextView)view.findViewById(R.id.pressure)).setText(String.valueOf(weather.pressure) + " мм.рт.ст.");
            ((TextView)view.findViewById(R.id.humidity)).setText(String.valueOf(weather.humidity) + " %");
            ((TextView)view.findViewById(R.id.weather)).setText(weather.weather);

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
