package com.example.taskdemo.ui.tab.photo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.gallerymodels.response.GalleryData;
import com.example.taskdemo.gallerymodels.response.GalleryDataResponse;
import com.example.taskdemo.webservices.gallery.GalleryApiClient;
import com.example.taskdemo.webservices.gallery.GalleryApiInterface;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoViewModel extends ViewModel {
    private MutableLiveData<List<GalleryData>> galleryDataList = new MutableLiveData<>();

    public LiveData<List<GalleryData>> getGalleryResponseObserver() {
        return galleryDataList;
    }

    public void  getGalleryDetailsList(int currentPage){

        GalleryApiInterface galleryApiInterface = GalleryApiClient.getRetroClient().create(GalleryApiInterface.class);
        galleryApiInterface.getMakeUpPhotos("-QOacAK-qA_4IM9TndEqgbb3hX78MrQ_mJaDwNNMaQ4","landscape",currentPage).enqueue(new Callback<GalleryDataResponse>() {
            @Override
            public void onResponse(Call<GalleryDataResponse> call, Response<GalleryDataResponse> response) {
                if(response.isSuccessful()){
                    GalleryDataResponse galleryDataResponse = response.body();
                    Gson gson = new Gson();
                    String Galleryresponse = gson.toJson(galleryDataResponse);
                    Log.d("GalleryResponse",Galleryresponse);
                    galleryDataList.postValue(galleryDataResponse.getResults());

                }
            }

            @Override
            public void onFailure(Call<GalleryDataResponse> call, Throwable t) {

            }
        });

    }

}