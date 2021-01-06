package com.example.carsharingapp.database.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RideAndCard {
    @Embedded public Ride ride;
    @Relation(
            parentColumn = "creditCardId",
            entityColumn = "id"
    )
    public List<CreditCard> card;

    @Override
    public String toString() {
        return "RideAndCard{" +
                "ride=" + ride +
                ", card=" + card +
                '}';
    }
}
