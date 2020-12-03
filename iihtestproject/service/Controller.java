package com.example.iihtestproject.service;

import com.example.iihtestproject.object.AppResponse;
import com.example.iihtestproject.object.CarListResponseEvent;
import com.example.iihtestproject.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    private AppApi mApi;

    public Controller(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        // logs
        HttpLoggingInterceptor interceptorLogger = new HttpLoggingInterceptor();
        interceptorLogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptorLogger)
                .build();
        String BASE_URL = AppConstant.BASE_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApi = retrofit.create(AppApi.class);
    }

    public void getCarList(){
        mApi.getCarList().enqueue(new Callback<CarListResponseEvent>() {
            @Override
            public void onResponse(Call<CarListResponseEvent> call, retrofit2.Response<CarListResponseEvent> response) {
                if (response.isSuccessful()){
                    CarListResponseEvent res = response.body();
                    onSuccess(res);
                }else {
                    CarListResponseEvent res = new CarListResponseEvent();
                    onError(res,null);
                }
            }

            @Override
            public void onFailure(Call<CarListResponseEvent> call, Throwable t) {
                CarListResponseEvent res = new CarListResponseEvent();
                onError(res,t);
            }
        });
    }

    /**
     * @param appResponse success data response
     */
    private void onSuccess(AppResponse appResponse){
        appResponse.setCode(1);
        EventBus.getDefault().post(appResponse);
    }

    /**
     * @param appResponse error data response
     */
    private void onError(AppResponse appResponse,Throwable throwable){
        appResponse.setError(0,throwable);
        EventBus.getDefault().post(appResponse);
    }
}
