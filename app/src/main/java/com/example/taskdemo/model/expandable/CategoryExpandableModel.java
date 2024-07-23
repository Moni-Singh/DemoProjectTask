package com.example.taskdemo.model.expandable;


import java.util.List;

public class CategoryExpandableModel {
    private CategoryProductData categoryProduct;
    private String categoryName;
    private  String categorySlug;
    private boolean isExpandable;

    public CategoryExpandableModel(CategoryProductData categoryProduct, String itemText, String categorySlug) {
        this.categoryProduct = categoryProduct;
        this.categoryName = itemText;
        this.categorySlug = categorySlug;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public CategoryProductData getCategoryProduct() {
        return categoryProduct;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategoryProduct(CategoryProductData categoryProduct) {
        this.categoryProduct = categoryProduct;
    }
    public void setCategoryId(String categoryId) {
        this.categorySlug = categoryId;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

}
