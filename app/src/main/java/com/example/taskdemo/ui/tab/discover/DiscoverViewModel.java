package com.example.taskdemo.ui.tab.discover;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.webservices.ApiClient;
import com.example.taskdemo.webservices.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverViewModel extends ViewModel {
    private MutableLiveData<Map<String, List<Product>>> productCategoryLiveData = new MutableLiveData<>();

    public MutableLiveData<Map<String, List<Product>>> getProductCategoryLiveData() {
        return productCategoryLiveData;
    }

    private MutableLiveData<List<Product>> productsByLimitLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Product>> getProductsByLimitLiveData() {
        return productsByLimitLiveData;
    }

    //Method to call product api
    public void getProductsApi() {
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    Map<String, List<Product>> productCategory = new HashMap<>();
                    products.forEach(it -> {
                        if (productCategory.containsKey(it.getCategory())) {
                            productCategory.get(it.getCategory()).add(it);
                        } else {
                            List<Product> productList = new ArrayList<>();
                            productList.add(it);
                            productCategory.put(it.getCategory(), productList);
                        }
                    });
                    productCategoryLiveData.postValue(productCategory);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productCategoryLiveData.postValue(null);
            }
        });
    }

    //Method to get the product by limit
    public void getProductByLimit() {
        int limit = 4;
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getProducts(limit).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productsByLimitLiveData.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productCategoryLiveData.postValue(null);
            }
        });
    }
}