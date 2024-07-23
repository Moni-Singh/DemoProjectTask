package com.example.taskdemo.model.expandable;


import android.os.Parcel;
import android.os.Parcelable;

public class Dimensions implements Parcelable {
    public double depth;
    public double width;
    public double height;

    protected Dimensions(Parcel in) {
        depth = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(depth);
        dest.writeDouble(width);
        dest.writeDouble(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dimensions> CREATOR = new Creator<Dimensions>() {
        @Override
        public Dimensions createFromParcel(Parcel in) {
            return new Dimensions(in);
        }

        @Override
        public Dimensions[] newArray(int size) {
            return new Dimensions[size];
        }
    };
}