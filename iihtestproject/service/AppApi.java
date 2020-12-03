package com.example.iihtestproject.service;

import com.example.iihtestproject.object.CarListResponseEvent;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppApi {
    @GET("itemsData.json")
    Call<CarListResponseEvent> getCarList();
}
