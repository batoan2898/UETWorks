package com.uet.uetworks.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static Api api;

    public static Api getApi(){
        if (api ==null){
            api = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.121/chat1902e/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);
        }
        return api;
    }
}
