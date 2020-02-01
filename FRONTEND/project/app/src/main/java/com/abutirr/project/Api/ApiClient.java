package com.abutirr.project.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL =
            "http://192.168.0.106:1234/project/myproject/public/api/";

    private static ApiClient apiClient;
    private Retrofit retrofit;
    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if(apiClient == null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public Api getApi(){

        return retrofit.create(Api.class);

    }






}
