package com.example.grocerywithjava;


import androidx.annotation.NonNull;

public class Grocery_Class {

    private double price;

    private String name;

    private String description;

    private String unit;

    public Grocery_Class() {

    }

    public Grocery_Class(double price, String name, String description, String unit) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + "\nUnit: " + unit + "\nPrice: " + price + "\nDescription: " + description;
    }
}
