package com.example.taskdemo.model.category;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Product implements Parcelable {
    private int id;
    private String title;
    private int price;
    private String description;
    private List<String> images;
    private String creationAt;
    private String updatedAt;
    private CategoryResponse category;

    public Product(int id, String title, int price, String description, List<String> images, String creationAt, String updatedAt, CategoryResponse category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.images = images;
        this.creationAt = creationAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(String creationAt) {
        this.creationAt = creationAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }


    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        price = in.readInt();
        description = in.readString();
        images = in.createStringArrayList();
        creationAt = in.readString();
        updatedAt = in.readString();
        category = in.readParcelable(CategoryResponse.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeString(description);
        dest.writeStringList(images);
        dest.writeString(creationAt);
        dest.writeString(updatedAt);
        dest.writeParcelable(category, flags);
    }
}
