package com.raxee.raxeeweather.manager;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.raxee.raxeeweather.model.CityModel;

import java.util.List;

public class CityManager {
    public static CityModel[] get() {
        CityModel[] cityModels = DB.getCityModels();
        if (cityModels.length == 0) {
            CityModel cityModel = new CityModel("Москва");
            DB.addCityModel(cityModel);
            cityModels = new CityModel[]{ cityModel };
        }
        return cityModels;
    }

    public static void add(String city) {
        DB.addCityModel(new CityModel(city));
    }

    public static void delete(String city) {
        DB.deleteCityModel(city);
    }

    private static class DB {
        public static CityModel[] getCityModels() {
            List<CityModel> cityModelList = new Select()
                    .from(CityModel.class)
                    .execute();
            CityModel[] cityModels = cityModelList.toArray(new CityModel[cityModelList.size()]);
            return cityModels;
        }

        public static void addCityModel(CityModel cityModel) {
            cityModel.save();
        }

        public static void deleteCityModel(String city) {
            new Delete()
                    .from(CityModel.class)
                    .where("city = ?", city)
                    .execute();
        }
    }
}
