package com.example.taskdemo.webservices.expandable;


import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.model.expandable.ProductCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    //GET PRODUCT CATEGORY
    @GET(AppUrls.GET_PRODUCT_CATEGORY)
    Call<List<ProductCategoryResponse>> getCategoryList();

    //GET PRODUCT CATEGORY LIST
    @GET(AppUrls.GET_PRODUCT_CATEGORY_LIST)
    Call<CategoryProductData> getProductsByCategory(@Path("category") String category);

}
