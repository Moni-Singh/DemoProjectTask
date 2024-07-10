package com.example.taskdemo.model.category;

import java.util.List;

public class Expandable {

    private List<Product> categoryProduct;
    private String categoryName;
    private  int categoryId;
    private boolean isExpandable;

    public Expandable(List<Product> itemList, String itemText,int categoryId) {
        this.categoryProduct = itemList;
        this.categoryName = itemText;
        this.categoryId = categoryId;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<Product> getCategoryProduct() {
        return categoryProduct;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryProduct(List<Product> categoryProduct) {
        this.categoryProduct = categoryProduct;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

}