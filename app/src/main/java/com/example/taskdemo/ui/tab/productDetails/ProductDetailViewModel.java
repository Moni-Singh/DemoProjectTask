package com.example.taskdemo.ui.tab.productDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.model.response.ProductDetails;
import com.example.taskdemo.webservices.ApiClient;
import com.example.taskdemo.webservices.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productCategoryLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Product>> getProductCategoryLiveData() {
        return productCategoryLiveData;
    }

    private MutableLiveData<ProductDetails> productDetailsList;

    public ProductDetailViewModel() {
        productDetailsList = new MutableLiveData<>();
    }

    public MutableLiveData<ProductDetails> getProductDetailsObserver() {
        return productDetailsList;
    }

    //Method to call product api
    public void getProductsApi() {
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productCategoryLiveData.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productCategoryLiveData.postValue(null);
            }
        });
    }
}