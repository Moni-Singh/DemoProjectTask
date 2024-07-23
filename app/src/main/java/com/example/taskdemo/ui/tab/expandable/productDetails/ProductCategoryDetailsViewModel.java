package com.example.taskdemo.ui.tab.expandable.productDetails;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.webservices.expandable.ApiClient;
import com.example.taskdemo.webservices.expandable.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryDetailsViewModel extends ViewModel {

    private MutableLiveData<CategoryProductData> categoryProductLiveData = new MutableLiveData<>();

    public LiveData<CategoryProductData> getCategoryProductLiveData(){
        return  categoryProductLiveData;
    }

    //Calling api to get product category
    public  void  getProductByCategory(String categoryName){
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getProductsByCategory(categoryName).enqueue(new Callback<CategoryProductData>() {
            @Override
            public void onResponse(Call<CategoryProductData> call, Response<CategoryProductData> response) {
                if(response.isSuccessful()){
                    CategoryProductData categoryProductData = response.body();
                    categoryProductLiveData.postValue(categoryProductData);
                }
            }
            @Override
            public void onFailure(Call<CategoryProductData> call, Throwable t) {
                categoryProductLiveData.postValue(null);
            }
        });}

}