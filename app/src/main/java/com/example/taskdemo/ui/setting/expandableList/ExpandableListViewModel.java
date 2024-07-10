package com.example.taskdemo.ui.setting.expandableList;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.taskdemo.model.category.CategoryResponse;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.webservices.category.CategoryApiClient;
import com.example.taskdemo.webservices.category.CategoryApiInterface;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpandableListViewModel extends ViewModel {
    private MutableLiveData<List<CategoryResponse>> categoriesLiveData = new MutableLiveData<>();

    public LiveData<List<CategoryResponse>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    private final MutableLiveData<List<Product>> productCategoryList = new MutableLiveData<>();
    public LiveData<List<Product>> getPoductCategoryLiveData(){
        return  productCategoryList;
    }


    public void getProductCategoryApi() {
        CategoryApiInterface apiInterface = CategoryApiClient.getRetroClient().create(CategoryApiInterface.class);

        apiInterface.getProductCategoryApi().enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<CategoryResponse> categoryResponses = response.body();
                    categoriesLiveData.postValue(categoryResponses);
                    Gson gson = new Gson();
                    String categoryResponsess = gson.toJson(categoryResponses);
                    Log.d("CategoryResposne", categoryResponsess);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });

    }

    public void getCategoryProducts() {
        CategoryApiInterface apiInterface = CategoryApiClient.getRetroClient().create(CategoryApiInterface.class);

        apiInterface.getCategoryProducts(1).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();
                    productCategoryList.postValue(products);
                    Gson gson = new Gson();
                    String respconse  = gson.toJson(products);
                    Log.d("jdeghc",respconse);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

}