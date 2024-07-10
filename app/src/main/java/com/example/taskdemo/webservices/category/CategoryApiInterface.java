package com.example.taskdemo.webservices.category;


import com.example.taskdemo.model.category.CategoryResponse;
import com.example.taskdemo.model.category.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryApiInterface {


    @GET(CategoryAppUrls.CATEGORY)
    Call<List<CategoryResponse>> getProductCategoryApi();

    @GET(CategoryAppUrls.CATEGORY_PRODUCT)
    Call<List<Product>> getCategoryProducts(@Path("id") int id);
}
