package com.example.taskdemo.ui.auth.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.request.LoginRequest;
import com.example.taskdemo.model.response.LoginResponse;
import com.example.taskdemo.webservices.ApiClient;
import com.example.taskdemo.webservices.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginResponseList;

    public LoginViewModel() {
        loginResponseList = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> getLoginResponseObserver() {
        return loginResponseList;
    }

    public void performLogin(LoginRequest loginRequest) {
        ApiInterface  apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        loginResponseList.postValue(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponseList.postValue(null);
            }
        });
    }
}