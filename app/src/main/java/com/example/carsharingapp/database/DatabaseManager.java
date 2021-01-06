package com.example.carsharingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.carsharingapp.database.dao.CreditCardDao;
import com.example.carsharingapp.database.dao.RideDao;
import com.example.carsharingapp.database.dao.UserDao;
import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.database.models.Ride;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.util.DateConverter;

@Database(entities = {User.class, CreditCard.class, Ride.class}, version = 4, exportSchema = false)
@TypeConverters({DateConverter.class})
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
    public abstract CreditCardDao getCreditCardDao();
    public abstract RideDao getRideDao();
}
