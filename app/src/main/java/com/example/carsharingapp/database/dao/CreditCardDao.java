package com.example.carsharingapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carsharingapp.database.models.CreditCard;

import java.util.List;

@Dao
public interface CreditCardDao {

    @Insert
    long insertCard(CreditCard card);

    @Query("SELECT * FROM CREDCARDS WHERE userId = :userId;")
    List<CreditCard> findAll(long userId);

    @Delete
    int delete(CreditCard card);

    @Update
    int update(CreditCard card);
}
