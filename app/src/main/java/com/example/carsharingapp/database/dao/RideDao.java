package com.example.carsharingapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.carsharingapp.database.models.Ride;
import com.example.carsharingapp.database.models.RideAndCard;

import java.util.List;

@Dao
public interface RideDao {

    @Insert
    long insertRide(Ride ride);

    @Transaction
    @Query("Select * from Ride where userId = :userId ;")
    List<RideAndCard> getRides(long userId);

}
