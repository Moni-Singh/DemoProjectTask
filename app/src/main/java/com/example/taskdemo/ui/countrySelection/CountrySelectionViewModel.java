package com.example.taskdemo.ui.countrySelection;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.country.request.CityRequest;
import com.example.taskdemo.model.country.request.StateRequest;
import com.example.taskdemo.model.country.response.CityResponse;
import com.example.taskdemo.model.country.response.Country;
import com.example.taskdemo.model.country.response.CountryDataResponse;
import com.example.taskdemo.model.country.response.StateResponse;
import com.example.taskdemo.webservices.country.CountryApiClient;
import com.example.taskdemo.webservices.country.CountryApiInterface;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountrySelectionViewModel extends ViewModel {
    private MutableLiveData<CountryDataResponse> countryDataResponseList = new MutableLiveData<>();

    public MutableLiveData<CountryDataResponse> getContryListRespsneObserver() {
        return countryDataResponseList;
    }

    private MutableLiveData<StateResponse> stateDataResponseList = new MutableLiveData<>();

    public MutableLiveData<StateResponse> getStateListObserver() {
        return stateDataResponseList;
    }

    private MutableLiveData<CityResponse> cityDataResposneList = new MutableLiveData<>();

    public MutableLiveData<CityResponse> getCityListObserver() {
        return cityDataResposneList;
    }

    public void getCountryList() {
        Log.d("hello", "enterd");
        CountryApiInterface apiInterface = CountryApiClient.getRetroClient().create(CountryApiInterface.class);
        apiInterface.getCountry().enqueue(new Callback<CountryDataResponse>() {
            @Override
            public void onResponse(Call<CountryDataResponse> call, Response<CountryDataResponse> response) {
                if (response.isSuccessful()) {
                    CountryDataResponse dataResponse = response.body();
                    if (dataResponse != null && !dataResponse.isError()) {
                        List<Country> countries = dataResponse.getData();
                        countryDataResponseList.postValue(dataResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDataResponse> call, Throwable t) {
                countryDataResponseList.postValue(null);
            }
        });
    }

    public void getStateList(String country) {

        StateRequest stateRequest = new StateRequest(country);
        CountryApiInterface apiInterface = CountryApiClient.getRetroClient().create(CountryApiInterface.class);
        apiInterface.getState(stateRequest).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response.isSuccessful()) {
                    StateResponse dataResponse = response.body();
                    if (dataResponse != null) {
                        stateDataResponseList.postValue(dataResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                stateDataResponseList.postValue(null);
            }
        });
    }

    public void getCitiesList(String country, String state) {

        CityRequest cityRequest = new CityRequest(country, state);
        CountryApiInterface apiInterface = CountryApiClient.getRetroClient().create(CountryApiInterface.class);
        apiInterface.getCities(cityRequest).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    CityResponse dataResponse = response.body();
                    cityDataResposneList.postValue(dataResponse);
                }
            }
            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                cityDataResposneList.postValue(null);
            }
        });
    }

}