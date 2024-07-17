package com.example.taskdemo.model.category;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryResponse implements Parcelable {

    private int id;
    private String name;
    private String image;
    private String creationAt;
    private String updatedAt;

    public CategoryResponse(int id, String name, String image, String creationAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.creationAt = creationAt;
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreationAt(String creationAt) {
        this.creationAt = creationAt;
    }

    public String getCreationAt() {
        return creationAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    protected CategoryResponse(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        creationAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<CategoryResponse> CREATOR = new Creator<CategoryResponse>() {
        @Override
        public CategoryResponse createFromParcel(Parcel in) {
            return new CategoryResponse(in);
        }

        @Override
        public CategoryResponse[] newArray(int size) {
            return new CategoryResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(creationAt);
        dest.writeString(updatedAt);
    }
}
