package com.example.proiect;


public class Car {
    String brand;
    String model;
    String fuelType;
    int prodYear;
    float capacity;
    int noSeats;
    int Km;
    String color;

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", prodYear=" + prodYear +
                ", capacity=" + capacity +
                ", noSeats=" + noSeats +
                ", Km=" + Km +
                ", color='" + color + '\'' +
                '}';
    }

    public Car(String brand, String model, String fuelType, int prodYear, float capacity, int noSeats, int km, String color) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.prodYear = prodYear;
        this.capacity = capacity;
        this.noSeats = noSeats;
        Km = km;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getProdYear() {
        return prodYear;
    }

    public void setProdYear(int prodYear) {
        this.prodYear = prodYear;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public int getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(int noSeats) {
        this.noSeats = noSeats;
    }

    public int getKm() {
        return Km;
    }

    public void setKm(int km) {
        Km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
