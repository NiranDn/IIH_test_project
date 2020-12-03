package com.example.iihtestproject.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CarListResponseEvent extends AppResponse {
    @SerializedName("placemarks")
    private ArrayList<Car> cars;

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }
}
