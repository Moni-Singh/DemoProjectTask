package com.example.taskdemo.productinterface;

import com.example.taskdemo.model.expandable.CategoryProduct;
import com.example.taskdemo.model.expandable.CategoryProductData;

public interface OnClickCategoryItem {
  void  onItemClick(String categoryName, CategoryProductData product, int productId);
}
