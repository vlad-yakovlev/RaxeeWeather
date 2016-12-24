package com.raxee.raxeeweather.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "City")
public class CityModel extends Model implements Serializable {
    @Column(name = "city")
    public String city;

    public CityModel() {
        super();
    }

    public CityModel(String city) {
        super();

        this.city = city;
    }
}
