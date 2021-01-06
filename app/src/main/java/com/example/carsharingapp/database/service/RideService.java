package com.example.carsharingapp.database.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carsharingapp.LoginActivity;
import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.DatabaseManager;
import com.example.carsharingapp.database.dao.RideDao;
import com.example.carsharingapp.database.models.Ride;
import com.example.carsharingapp.database.models.RideAndCard;

import java.util.List;
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


    public void getRides(Callback<List<RideAndCard>> callback){
        long userId = sharedPref.getLong(LoginActivity.USER_ID, -1);
        Callable<List<RideAndCard>> callable = new Callable<List<RideAndCard>>() {
            @Override
            public List<RideAndCard> call() throws Exception {
                if(userId == -1){
                    return null;
                }
                return  rideDao.getRides(userId);
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
