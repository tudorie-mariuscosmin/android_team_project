package com.example.carsharingapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Car extends ArrayList<Parcelable> implements Parcelable {

    private String title;
    private String state;
    private String type;
    private int kilometers;

    public Car(String title, String state, String type, int kilometers) {
        this.title = title;
        this.state = state;
        this.type = type;
        this.kilometers = kilometers;
    }

    protected Car(Parcel in) {
        title = in.readString();
        state = in.readString();
        type = in.readString();
        kilometers = in.readInt();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @Override
    public String toString() {
        return "Car{" +
                "title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", kilometers=" + kilometers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(state);
        dest.writeString(type);
        dest.writeInt(kilometers);
    }
}
