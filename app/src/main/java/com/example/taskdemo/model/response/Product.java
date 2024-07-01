package com.example.taskdemo.model.response;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;
    private int type = 0;
    private boolean isLiked;
    private boolean isCategory = false;
    private boolean isSlider =false;
    private List<Product> productList;
    public Product(){};

    public Product(int id, String title, double price, String description, String category, boolean isLiked, String image, Rating rating, int type) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.isLiked = isLiked;
        this.image = image;
        this.rating = rating;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    public void setType(int type){
        this.type = type;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public int getType() {
        return type;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    public boolean isSlider() {
        return isSlider;
    }

    public void setSlider(boolean slider) {
        isSlider = slider;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
