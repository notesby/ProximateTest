package com.justforfun.proximatetest;

import android.app.Application;

import com.justforfun.proximatetest.network.RESTService;

import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hectormoreno on 9/5/17.
 */

public class MyApplication extends Application {

    private static Retrofit retrofit;
    private static RESTService service;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://serveless.proximateapps-services.com.mx/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();

        service = retrofit.create(RESTService.class);

        Realm.init(this);
    }


    public static Retrofit getRetrofit(){
        return  retrofit;
    }

    public static RESTService getService(){
        return service;
    }
}
