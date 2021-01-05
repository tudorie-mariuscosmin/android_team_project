package com.example.carsharingapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarJSONParser {

    public static final String TITLE = "Title";
    public static final String TYPE = "Type";
    public static final String STATE = "State";
    public static final String KILOMETERS = "Kilometers";

    public static ArrayList<Car> fromJson(String json) {

        ArrayList<Car> cars = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray carsArray = object.getJSONArray("cars");
            for(int i=0; i<carsArray.length();i++){
                Car car = readCar(carsArray.getJSONObject(i));
                cars.add(car);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private static Car readCar(JSONObject object) throws JSONException{
        String title = object.getString(TITLE);
        String type = object.getString(TYPE);
        String state = object.getString(STATE);
        int kilometers = object.getInt(KILOMETERS);

        return  new Car(title, type, state, kilometers);

    }
}
