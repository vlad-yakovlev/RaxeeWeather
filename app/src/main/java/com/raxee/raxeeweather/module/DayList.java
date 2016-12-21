package com.raxee.raxeeweather.module;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.api.WeatherData;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DayList {
    public static void draw(Context context, Resources resources, ListView list, int layout, WeatherData[] data) {
        list.setAdapter(new Adapter(context, layout, data));
        list.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.length * 49 - 1, resources.getDisplayMetrics());
    }

    private static class Adapter extends ArrayAdapter<WeatherData> {
        private final Context context;
        private final int layout;
        private WeatherData[] data = null;

        public Adapter(Context context, int layout, WeatherData[] data) {
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
            ((TextView)view.findViewById(R.id.forecast_datetime)).setText(String.format(Locale.getDefault(), "%S", dateDormat.format(weather.datetime)));

            ((ImageView)view.findViewById(R.id.forecast_icon)).setImageResource(weather.icon);

            ((TextView)view.findViewById(R.id.forecast_temperature)).setText(String.format(Locale.getDefault(), "%d", weather.temperature));
            ((TextView)view.findViewById(R.id.forecast_feel_temperature)).setText(String.format(Locale.getDefault(), "%d", weather.feelTemperature));

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
