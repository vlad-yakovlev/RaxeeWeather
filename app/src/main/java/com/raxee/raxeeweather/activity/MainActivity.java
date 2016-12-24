package com.raxee.raxeeweather.activity;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Keyboard.activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationManager = new NavigationManager(this, toolbar, new NavigationManager.OnClickListener() {
            @Override
            public void onClick(CityModel cityModel) {
                setWeather(cityModel);
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

        FloatingActionButton addCityButton = (FloatingActionButton)findViewById(R.id.add_city);

        ((FloatingActionButton)findViewById(R.id.add_city)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddCity();
            }
        });
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
            case android.R.id.home:
                navigationManager.open();
                return true;

            case R.id.city_delete:
                CityManager.delete(cityModel.city);
                navigationManager.build();
                navigationManager.select(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setWeather(CityModel cityModel) {
        getSupportActionBar().setTitle(cityModel.city);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавление города");

        final EditText input = new EditText(this);
        input.setHint("Название города");
        builder.setView(input);

        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityManager.add(input.getText().toString());
                navigationManager.build();
                navigationManager.select(navigationManager.size() - 1);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
