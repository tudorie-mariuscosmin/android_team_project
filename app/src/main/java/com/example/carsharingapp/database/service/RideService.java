package com.example.carsharingapp.database.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carsharingapp.LoginActivity;
import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.DatabaseManager;
import com.example.carsharingapp.database.dao.RideDao;
import com.example.carsharingapp.database.models.Ride;

import java.util.concurrent.Callable;

public class RideService {
    private RideDao  rideDao;
    private AsyncTaskRunner asyncTaskRunner;
    private SharedPreferences sharedPref;
    public RideService(Context context){
        rideDao = DatabaseManager.getInstance(context).getRideDao();
        asyncTaskRunner = new AsyncTaskRunner();
        sharedPref = context.getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void insertRide(Ride ride, Callback<Ride> callback){
        Callable<Ride> callable = new Callable<Ride>() {
            @Override
            public Ride call() throws Exception {
                if(ride == null){
                    return null;
                }
                long id =  rideDao.insertRide(ride);
                if(id == -1){
                    return null;
                }
                ride.setId(id);
                return ride;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
