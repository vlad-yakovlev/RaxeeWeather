package com.raxee.raxeeweather.list;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
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

import static android.R.id.list;

public class ForecastList {
    private Adapter adapter;
    private Context context;
    private ListView list;

    public ForecastList(Context context, ListView list, int layout) {
        this.context = context;
        this.list = list;

        adapter = new Adapter(context, layout, new WeatherModel[0]);
        list.setAdapter(adapter);
    }

    public void set(WeatherModel[] data) {
        adapter.data = data;
        adapter.notifyDataSetChanged();

        list.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.length * 49 - 1, context.getResources().getDisplayMetrics());
    }

    private class Adapter extends ArrayAdapter<WeatherModel> {
        private final Context context;
        private final int layout;
        public WeatherModel[] data = null;

        public Adapter(Context context, int layout, WeatherModel[] data) {
            super(context, layout, data);
            this.context = context;
            this.layout = layout;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layout, parent, false);

            WeatherModel weather = data[position];

            SimpleDateFormat dateDormat = new SimpleDateFormat("EE HH:mm");
            ((TextView)view.findViewById(R.id.datetime)).setText(String.format(Locale.getDefault(), "%S", dateDormat.format(weather.datetime)));
            ((TextView)view.findViewById(R.id.icon)).setText(weather.icon);
            ((TextView)view.findViewById(R.id.temperature)).setText(String.format(Locale.getDefault(), "%d", weather.temperature));

            return view;
        }

        @Override
        public int getCount() {
            return data.length;
        }
    }
}
