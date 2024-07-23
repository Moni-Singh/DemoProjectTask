package com.example.taskdemo.ui.tab.expandable;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.model.expandable.ProductCategoryResponse;
import com.example.taskdemo.webservices.expandable.ApiClient;
import com.example.taskdemo.webservices.expandable.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpandableViewModel extends ViewModel {

    private MutableLiveData<List<ProductCategoryResponse>> categoriesLiveData = new MutableLiveData<>();

    public LiveData<List<ProductCategoryResponse>> getCategoriesLiveData() {
        return categoriesLiveData;
    }
    private MutableLiveData<CategoryProductData> categoryProductLiveData = new MutableLiveData<>();

    public LiveData<CategoryProductData> getCategoryProductLiveData(){
        return  categoryProductLiveData;
    }


    //Method to call product api
    public void getProductsApi() {
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getCategoryList().enqueue(new Callback<List<ProductCategoryResponse>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryResponse>> call, Response<List<ProductCategoryResponse>> response) {
                if(response.isSuccessful()){
                    List<ProductCategoryResponse> productCategoryResponses = response.body();
                    Gson gson = new Gson();
                    String resposne = gson.toJson(productCategoryResponses);

                    categoriesLiveData.postValue(productCategoryResponses);
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryResponse>> call, Throwable t) {
                categoriesLiveData.postValue(null);
            }
        });
    }

//Method to call api of product by category
    public  void  getProductByCategory(String categoryName){
        ApiInterface apiInterface = ApiClient.getRetroClient().create(ApiInterface.class);
        apiInterface.getProductsByCategory(categoryName).enqueue(new Callback<CategoryProductData>() {
            @Override
            public void onResponse(Call<CategoryProductData> call, Response<CategoryProductData> response) {
                if(response.isSuccessful()){
                    CategoryProductData categoryProductData = response.body();

                    Gson gson =new Gson();
                    String resposne = gson.toJson(categoryProductData);
                    Log.d("resposneDataCategory",resposne);
                    categoryProductLiveData.postValue(categoryProductData);
                } else {
                    Log.e("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CategoryProductData> call, Throwable t) {
                Log.e("API Error", "Failed to fetch data", t);
            }
        });}

}