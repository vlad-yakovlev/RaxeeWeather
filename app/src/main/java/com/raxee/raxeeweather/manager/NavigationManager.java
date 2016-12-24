package com.raxee.raxeeweather.manager;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        void onClick(CityModel cityModel);
    }

    public NavigationManager(final AppCompatActivity activity, Toolbar toolbar, final OnClickListener onClickListener) {
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

                onClickListener.onClick(cityModels[i]);

                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(activity, drawer.getDrawerLayout(), toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                Keyboard.hide();
            }
        };

        drawer.setActionBarDrawerToggle(actionBarDrawerToggle);

        drawer.getDrawerLayout().addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void build() {
        drawer.removeAllItems();
        CityModel[] cityModels = CityManager.get();
        for (Integer i = 0; i < cityModels.length; i++) {
            drawer.addItem(new PrimaryDrawerItem().withName(cityModels[i].city).withIdentifier(i));
        }

        length = cityModels.length;
    }

    public Integer size() {
        return length;
    }

    public void select(Integer id) {
        drawer.setSelection(id, true);
    }

    public void open() {
        drawer.openDrawer();
    }
}
