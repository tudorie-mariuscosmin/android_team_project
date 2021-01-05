package com.example.carsharingapp.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityJSONParser {

    public static final String NAME = "Name";
    public static final String LONG = "Long";
    public static final String LAT = "Lat";

    public static ArrayList<City> fromJson(String json) {

        ArrayList<City> cities = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray citiesArray = object.getJSONArray("cities");

            Log.d("citiesArrray", citiesArray.toString());
            for(int i=0; i<citiesArray.length();i++){
                City city = readCity(citiesArray.getJSONObject(i));
                cities.add(city);

                Log.d("city", city.toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private static City readCity(JSONObject object) throws JSONException{

        String name = object.getString(NAME);
        double longitude = object.getDouble(LONG);
        double latitude = object.getDouble(LAT);

        Log.d("Longitude", String.valueOf(longitude));
        Log.d("Latitude", String.valueOf(latitude));
        Log.d("name", name);


        return new City(name, longitude, latitude);
    }
}
