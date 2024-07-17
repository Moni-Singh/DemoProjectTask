package com.example.taskdemo.ui.setting.productDetails;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.webservices.category.CategoryApiClient;
import com.example.taskdemo.webservices.category.CategoryApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryDetailsViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> productCategoryList = new MutableLiveData<>();
    public LiveData<List<Product>> getPoductCategoryLiveData(){
        return  productCategoryList;
    }

    public void getCategoryProducts(int categoryId) {
        CategoryApiInterface apiInterface = CategoryApiClient.getRetroClient().create(CategoryApiInterface.class);

        apiInterface.getCategoryProducts(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();
                    productCategoryList.postValue(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productCategoryList.postValue(null);
            }
        });
    }

}