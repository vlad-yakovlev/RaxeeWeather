package com.raxee.raxeeweather.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.raxee.raxeeweather.R;
import com.raxee.raxeeweather.model.CityModel;
import com.raxee.raxeeweather.module.Keyboard;

public class NavigationManager {
    private Drawer drawer;
    private Integer length = 0;

    public static interface OnClickListener {
        void onWeatherClick(CityModel cityModel);
        void onAddCityClick();
    }

    public NavigationManager(final Activity activity, final OnClickListener onClickListener) {
        /*setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(header)
                .build();

        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Integer i = (int)drawerItem.getIdentifier();
                CityModel[] cityModels = CityManager.get();

                if (i < cityModels.length) {
                    onClickListener.onWeatherClick(cityModels[i]);
                } else if (i == cityModels.length) {
                    onClickListener.onAddCityClick();
                }

                return false;
            }
        });

        drawer.setActionBarDrawerToggle(new ActionBarDrawerToggle(activity, drawer.getDrawerLayout(), 0, 0) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                Keyboard.hide();
            }
        });
    }

    public void build() {
        drawer.removeAllItems();
        CityModel[] cityModels = CityManager.get();
        for (Integer i = 0; i < cityModels.length; i++) {
            drawer.addItem(new PrimaryDrawerItem().withName(cityModels[i].city).withIdentifier(i));
        }
        drawer.addItems(
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName("Добавить город").withIdentifier(cityModels.length)
        );

        length = cityModels.length;
    }

    public Integer size() {
        return length;
    }

    public void select(Integer id) {
        drawer.setSelection(id, true);
    }
}
