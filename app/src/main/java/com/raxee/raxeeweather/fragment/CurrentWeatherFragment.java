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

public class CurrentWeatherFragment extends Fragment {
    private LinearLayout layoutView;
    private TextView temperatureView;
    private ListView weatherListView;

    public CurrentWeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        layoutView = (LinearLayout)view.findViewById(R.id.layout);

        temperatureView = (TextView)view.findViewById(R.id.temperature);
        weatherListView = (ListView)view.findViewById(R.id.weather_item_list);

        return view;
    }

    public void loadWeather(String city) {
        WeatherAPI.getInstance().getCurrentWeather(city, new WeatherAPI.OnCallbackListener() {
            public void onCallback(WeatherData[] current) {
                WeatherData weather = current[0];

                temperatureView.setText(String.format("%d°", weather.temperature));

                ListItem[] weatherList = {
                        new ListItem("Влажность вохдуха", String.format("%d %%", weather.humidity)),
                        new ListItem("Атмосферное давление", String.format("%d мм рт.ст.", weather.humidity)),
                        new ListItem("Ветер", String.format("%d м/с %s", weather.windSpeed, weather.windDirection)),
                };

                weatherListView.setAdapter(new CurrentWeatherFragment.ListAdapter(getActivity(), R.layout.layout_list_item, weatherList));
                weatherListView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, weatherList.length * 49 - 1, getResources().getDisplayMetrics());

                layoutView.setVisibility(View.VISIBLE);
            }
        });
    }

    public class ListItem {
        public String name = null;
        public String value = null;

        public ListItem() {}

        public ListItem(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    public class ListAdapter extends ArrayAdapter<ListItem> {
        private final Context context;
        private final int layout;
        private ListItem[] data = null;

        public ListAdapter(Context context, int layout, ListItem[] data) {
            super(context, layout, data);
            this.context = context;
            this.layout = layout;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layout, parent, false);
            ListItem item = data[position];

            ((TextView)view.findViewById(R.id.name)).setText(item.name);
            ((TextView)view.findViewById(R.id.value)).setText(item.value);

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
