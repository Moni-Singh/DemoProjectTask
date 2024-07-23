package com.example.taskdemo.model.expandable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CategoryProduct implements Parcelable {
    public int id;
    public String title;
    public String description;
    public String category;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public ArrayList<String> tags;
    public String brand;
    public String sku;
    public int weight;
    public Dimensions dimensions;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public ArrayList<Review> reviews;
    public String returnPolicy;
    public int minimumOrderQuantity;
    public Meta meta;
    public ArrayList<String> images;
    public String thumbnail;


    public CategoryProduct(int id,String title,String description,String category,double price, double discountPercentage,
                           double rating,int stock, ArrayList<String> tags,String brand, String sku,int weight,Dimensions dimension,
                           String warrantyInformation, String shippingInformation,String availabilityStatus,ArrayList<Review> reviews,String returnPolicy,
                           int minimumOrderQuantity,Meta meta,ArrayList<String> images,String thumbnail){
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.tags = tags;
        this.brand = brand;
        this.sku = sku;
        this.weight = weight;
        this.dimensions = dimension;
        this.warrantyInformation = warrantyInformation;
        this.shippingInformation = shippingInformation;
        this.availabilityStatus = availabilityStatus;
        this.reviews = reviews;
        this.returnPolicy = returnPolicy;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.meta = meta;
        this.images = images;
        this.thumbnail = thumbnail;

    }

    // Parcelable implementation
    protected CategoryProduct(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        price = in.readDouble();
        discountPercentage = in.readDouble();
        rating = in.readDouble();
        stock = in.readInt();
        tags = in.createStringArrayList();
        brand = in.readString();
        sku = in.readString();
        weight = in.readInt();
        dimensions = in.readParcelable(Dimensions.class.getClassLoader());
        warrantyInformation = in.readString();
        shippingInformation = in.readString();
        availabilityStatus = in.readString();
        reviews = in.createTypedArrayList(Review.CREATOR);
        returnPolicy = in.readString();
        minimumOrderQuantity = in.readInt();
        meta = in.readParcelable(Meta.class.getClassLoader());
        images = in.createStringArrayList();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeDouble(discountPercentage);
        dest.writeDouble(rating);
        dest.writeInt(stock);
        dest.writeStringList(tags);
        dest.writeString(brand);
        dest.writeString(sku);
        dest.writeInt(weight);
        dest.writeParcelable(dimensions, flags);
        dest.writeString(warrantyInformation);
        dest.writeString(shippingInformation);
        dest.writeString(availabilityStatus);
        dest.writeTypedList(reviews);
        dest.writeString(returnPolicy);
        dest.writeInt(minimumOrderQuantity);
        dest.writeParcelable(meta, flags);
        dest.writeStringList(images);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryProduct> CREATOR = new Creator<CategoryProduct>() {
        @Override
        public CategoryProduct createFromParcel(Parcel in) {
            return new CategoryProduct(in);
        }

        @Override
        public CategoryProduct[] newArray(int size) {
            return new CategoryProduct[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}