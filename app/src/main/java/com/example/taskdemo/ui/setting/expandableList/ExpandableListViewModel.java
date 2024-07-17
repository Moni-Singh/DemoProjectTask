package com.example.taskdemo.ui.setting.expandableList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.taskdemo.model.category.CategoryResponse;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.webservices.category.CategoryApiClient;
import com.example.taskdemo.webservices.category.CategoryApiInterface;

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

    public LiveData<List<Product>> getPoductCategoryLiveData() {
        return productCategoryList;
    }

    //Calling api to get Category
    public void getProductCategoryApi() {
        CategoryApiInterface apiInterface = CategoryApiClient.getRetroClient().create(CategoryApiInterface.class);

        apiInterface.getProductCategoryApi().enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<CategoryResponse> categoryResponses = response.body();
                    categoriesLiveData.postValue(categoryResponses);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                categoriesLiveData.postValue(null);
            }
        });
    }

    //Calling api to get CategoryProduct
    public void getCategoryProducts(int categoryId) {
        CategoryApiInterface apiInterface = CategoryApiClient.getRetroClient().create(CategoryApiInterface.class);

        apiInterface.getCategoryProducts(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productCategoryList.postValue(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                categoriesLiveData.postValue(null);
            }
        });
    }

}