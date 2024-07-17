package com.example.taskdemo.productinterface;


import com.example.taskdemo.model.category.Product;

public interface OnClickCategoryProduct {
    void onItemClick(int categoryId, Product product,int productId);
}
