package com.example.carsharingapp.database.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ride")
public class Ride {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "userId")
    private long userId;
    @ColumnInfo(name = "fromDate")
    private String fromDate;
    @ColumnInfo(name = "untilDate")
    private String untilDate;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "car")
    private String car;
    @ColumnInfo(name = "creditCardId")
    private long creditCardId;
    @ColumnInfo(name = "expectedKm")
    private int expectedKm;

    @Ignore
    public Ride(long userId, String fromDate, String untilDate, String location, String car, long creditCardId, int expectedKm) {
        this.userId = userId;
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.location = location;
        this.car = car;
        this.creditCardId = creditCardId;
        this.expectedKm = expectedKm;
    }

    public Ride(long id, long userId, String fromDate, String untilDate, String location, String car, long creditCardId, int expectedKm) {
        this.id = id;
        this.userId = userId;
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.location = location;
        this.car = car;
        this.creditCardId = creditCardId;
        this.expectedKm = expectedKm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(String untilDate) {
        this.untilDate = untilDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public int getExpectedKm() {
        return expectedKm;
    }

    public void setExpectedKm(int expectedKm) {
        this.expectedKm = expectedKm;
    }

    @Override
    public String toString() {
        return "Ride no." + id+ " : from " + fromDate + " untill " + untilDate
                + " with car " + car + " picked up in "+ location + " with expected km of " + expectedKm;
    }
}
