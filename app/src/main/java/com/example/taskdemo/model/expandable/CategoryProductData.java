package com.example.taskdemo.model.expandable;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CategoryProductData implements Parcelable {
    public ArrayList<CategoryProduct> products;
    public int total;
    public int skip;
    public int limit;

    // Default constructor
    public CategoryProductData() {
    }

    // Constructor used for parceling
    protected CategoryProductData(Parcel in) {
        products = in.createTypedArrayList(CategoryProduct.CREATOR);
        total = in.readInt();
        skip = in.readInt();
        limit = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(products);
        dest.writeInt(total);
        dest.writeInt(skip);
        dest.writeInt(limit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryProductData> CREATOR = new Creator<CategoryProductData>() {
        @Override
        public CategoryProductData createFromParcel(Parcel in) {
            return new CategoryProductData(in);
        }

        @Override
        public CategoryProductData[] newArray(int size) {
            return new CategoryProductData[size];
        }
    };
}
