package com.raxee.raxeeweather.list;

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

public class CurrentList {
    private Adapter adapter;
    private Context context;
    private ListView list;

    public CurrentList(Context context, ListView list, int layout) {
        this.context = context;
        this.list = list;

        adapter = new Adapter(context, layout, new CurrentList.Item[0]);
        list.setAdapter(adapter);
    }

    public void set(CurrentList.Item[] data) {
        adapter.data = data;
        adapter.notifyDataSetChanged();

        list.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.length * 49 - 1, context.getResources().getDisplayMetrics());
    }

    public static class Item {
        public String name = null;
        public String value = null;

        public Item(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private class Adapter extends ArrayAdapter<Item> {
        private final Context context;
        private final int layout;
        private Item[] data = null;

        public Adapter(Context context, int layout, Item[] data) {
            super(context, layout, data);
            this.context = context;
            this.layout = layout;
            this.data = data;
        }
        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View view = inflater.inflate(layout, parent, false);

            Item item = data[position];

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
