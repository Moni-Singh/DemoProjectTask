package com.example.taskdemo.model.expandable;

import android.os.Parcel;
import android.os.Parcelable;

public class Meta implements Parcelable {
    public String createdAt;
    public String updatedAt;
    public String barcode;
    public String qrCode;

    // Parcelable implementation
    protected Meta(Parcel in) {
        createdAt = in.readString();
        updatedAt = in.readString();
        barcode = in.readString();
        qrCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(barcode);
        dest.writeString(qrCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };
}
