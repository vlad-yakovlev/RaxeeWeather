package com.raxee.raxeeweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.fragment.WeatherFragment;
import com.raxee.raxeeweather.manager.CityManager;
import com.raxee.raxeeweather.manager.NavigationManager;
import com.raxee.raxeeweather.model.CityModel;
import com.raxee.raxeeweather.module.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NavigationManager navigationManager;

    private CityModel cityModel;

    private LinearLayout addCityLayout;
    private LinearLayout weatherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        Keyboard.activity = this;

        addCityLayout = (LinearLayout)findViewById(R.id.form_add_city);
        weatherLayout = (LinearLayout)findViewById(R.id.layout_weather);

        navigationManager = new NavigationManager(this, new NavigationManager.OnClickListener() {
            @Override
            public void onWeatherClick(CityModel cityModel) {
                setWeather(cityModel);
            }

            @Override
            public void onAddCityClick() {
                setAddCity();
            }
        });
        navigationManager.build();
        if (savedInstanceState != null) {
            List<String> cities = new ArrayList<String>();
            for (CityModel cityModel : CityManager.get()) {
                cities.add(cityModel.city);
            }
            navigationManager.select(Math.max(cities.indexOf(savedInstanceState.getString("city")), 0));
        } else {
            navigationManager.select(0);
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putString("city", cityModel.city);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.city_delete:
                CityManager.delete(cityModel.city);
                navigationManager.build();
                navigationManager.select(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setWeather(CityModel cityModel) {
        addCityLayout.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.VISIBLE);
        setToolbarTitle(cityModel.city);

        this.cityModel = cityModel;

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

        final TextView cityText = (TextView)findViewById(R.id.city_text);
        final Button cityAdd = (Button)findViewById(R.id.city_add);

        cityText.setText("");
        Keyboard.show(cityText);

        cityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keyboard.hide();
                CityManager.add(cityText.getText().toString());
                navigationManager.build();
                navigationManager.select(navigationManager.size() - 1);
            }
        });
    }
}
