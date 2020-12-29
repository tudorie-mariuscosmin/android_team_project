package com.example.carsharingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.carsharingapp.database.dao.UserDao;
import com.example.carsharingapp.database.models.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    public static final String CAR_SHARING_DB = "CarSharingDB";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context){
        if(databaseManager ==null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, CAR_SHARING_DB)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return  databaseManager;
    }

    public abstract UserDao getUserDao();
}
