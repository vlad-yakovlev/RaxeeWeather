package com.raxee.raxeeweather.module;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.model.WeatherModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DayList {
    public static void draw(Context context, ListView list, int layout, WeatherModel[] data) {
        list.setAdapter(new Adapter(context, layout, data));
        list.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.length * 49 - 1, context.getResources().getDisplayMetrics());
    }

    private static class Adapter extends ArrayAdapter<WeatherModel> {
        private final Context context;
        private final int layout;
        private WeatherModel[] data = null;

        public Adapter(Context context, int layout, WeatherModel[] data) {
            super(context, layout, data);
            this.context = context;
            this.layout = layout;
            this.data = data;
        }

        private class ItemView {
            public TextView datetime;
            public TextView icon;
            public TextView temperature;

            public ItemView(View view) {
                datetime = (TextView)view.findViewById(R.id.datetime);
                icon = (TextView)view.findViewById(R.id.icon);
                temperature = (TextView)view.findViewById(R.id.temperature);
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layout, parent, false);
            ItemView itemView = new ItemView(view);

            WeatherModel weather = data[position];

            SimpleDateFormat dateDormat = new SimpleDateFormat("EE HH:mm");
            itemView.datetime.setText(String.format(Locale.getDefault(), "%S", dateDormat.format(weather.datetime)));

            itemView.icon.setTypeface(FontManager.getTypeface(context, FontManager.WEATHERICONS));
            itemView.icon.setText(context.getResources().getString(weather.iconResource));

            itemView.temperature.setText(String.format(Locale.getDefault(), "%d", weather.temperature));

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
