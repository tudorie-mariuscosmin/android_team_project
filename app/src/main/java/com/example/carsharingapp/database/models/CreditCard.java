package com.example.carsharingapp.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "CredCards",  foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
public class CreditCard  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "userId")
    private long userId;
    @ColumnInfo(name = "cardholderName")
    private String cardholderName;
    @ColumnInfo(name = "cardNumber")
    private long cardNumber;
    @ColumnInfo(name = "expiration")
    private Date expiration;
    @ColumnInfo(name = "cvv")
    private int cvv;
    @ColumnInfo(name = "type")
    private String type;

    @Ignore
    public CreditCard(long userId, String cardholderName, long cardNumber, Date expiration, int cvv, String type) {
        this.userId = userId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
        this.type = type;
    }

    public CreditCard(long id, long userId, String cardholderName, long cardNumber, Date expiration, int cvv, String type) {
        this.id = id;
        this.userId = userId;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
        this.type = type;
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

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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
