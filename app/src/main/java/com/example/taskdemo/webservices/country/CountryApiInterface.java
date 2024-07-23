package com.example.taskdemo.webservices.country;

import com.example.taskdemo.model.country.request.CityRequest;
import com.example.taskdemo.model.country.request.StateRequest;
import com.example.taskdemo.model.country.response.CityResponse;
import com.example.taskdemo.model.country.response.CountryDataResponse;
import com.example.taskdemo.model.country.response.StateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CountryApiInterface {

    //GET COUNTRY
    @GET(CountryUrls.COUNTRY)
    Call<CountryDataResponse> getCountry();

    //GET STATE
    @POST(CountryUrls.STATE)
    Call<StateResponse>getState(@Body StateRequest stateRequest);

    //GET CITIES
    @POST(CountryUrls.CITIES)
    Call<CityResponse>getCities(@Body CityRequest cityRequest);

}
