package com.example.carsharingapp.database.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RideAndCard {
    @Embedded public Ride ride;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<CreditCard> card;
}
