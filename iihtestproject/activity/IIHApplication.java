package com.example.iihtestproject.activity;

import android.app.Application;

import com.example.iihtestproject.service.Controller;

public class IIHApplication extends Application {
    private static  IIHApplication mInstance;
    private Controller mController;

    public static  IIHApplication getInstance(){
        if (mInstance == null){
            mInstance = new IIHApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getController();
    }

    public Controller getController(){
        if (mController == null){
            mController = new Controller();
        }
        return mController;
    }
}
