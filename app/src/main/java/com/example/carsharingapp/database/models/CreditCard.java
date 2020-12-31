package com.example.carsharingapp.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "CredCards")
public class CreditCard {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ForeignKey()
    private long userId;

    private String cardholderName;

    private long cardNumber;

    private Date expiration;

    private int cvv;


    public CreditCard(long userId, String cardholderName, long cardNumber, Date expiration, int cvv) {
        this.userId = userId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
    }

    public CreditCard(long id, long userId, String cardholderName, long cardNumber, Date expiration, int cvv) {
        this.id = id;
        this.userId = userId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
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

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", userId=" + userId +
                ", cardholderName='" + cardholderName + '\'' +
                ", cardNumber=" + cardNumber +
                ", expiration=" + expiration +
                ", cvv=" + cvv +
                '}';
    }
}
