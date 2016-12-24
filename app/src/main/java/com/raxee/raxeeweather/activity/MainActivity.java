package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.WeatherFragment;
import com.raxee.raxeeweather.manager.CityManager;
import com.raxee.raxeeweather.model.CityModel;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Drawer drawer;

    private LinearLayout addCityLayout;
    private LinearLayout weatherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCityLayout = (LinearLayout)findViewById(R.id.form_add_city);
        weatherLayout = (LinearLayout)findViewById(R.id.layout_weather);

        initToolbar();
        initNavigationDrawer();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setToolbarTitle(String title) {
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);
    }

    private void initNavigationDrawer() {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(header)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Integer i = (int)drawerItem.getIdentifier();
                        CityModel[] cityModels = CityManager.get();

                        if (i < cityModels.length) {
                            setWeather(cityModels[i]);
                        } else if (i == cityModels.length) {
                            setAddCity();
                        }

                        return false;
                    }
                })
                .build();

        buildNavigationDriverList();
        drawer.setSelection(0, true);
    }

    private Integer buildNavigationDriverList() {
        drawer.removeAllItems();
        CityModel[] cityModels = CityManager.get();
        for (Integer i = 0; i < cityModels.length; i++) {
            drawer.addItem(new PrimaryDrawerItem().withName(cityModels[i].city).withIdentifier(i));
        }
        drawer.addItem(new DividerDrawerItem());
        drawer.addItem(new PrimaryDrawerItem().withName("Добавить город").withIdentifier(cityModels.length).withSelectable(false));

        return cityModels.length;
    }

    private void setWeather(CityModel cityModel) {
        addCityLayout.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.VISIBLE);
        setToolbarTitle(cityModel.city);

        final WeatherFragment weatherFragment = new WeatherFragment();

        Bundle arguments = new Bundle();
        arguments.putString("city", cityModel.city);
        weatherFragment.setArguments(arguments);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_weather, weatherFragment)
                .commit();
    }

    private void setAddCity() {
        addCityLayout.setVisibility(View.VISIBLE);
        weatherLayout.setVisibility(View.GONE);
        setToolbarTitle("Добавление города");

        ((TextView)findViewById(R.id.city)).setText("");

        ((Button)findViewById(R.id.add_city)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                CityManager.add(((TextView)findViewById(R.id.city)).getText().toString());
                drawer.setSelection(buildNavigationDriverList() - 1, true);
            }
        });
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
