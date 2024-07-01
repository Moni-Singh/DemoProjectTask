package com.example.taskdemo.webservices.gallery;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if(retrofit == null ) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(GalleryUrls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
