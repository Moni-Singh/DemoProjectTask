package com.example.taskdemo.webservices.country;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(CountryUrls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
