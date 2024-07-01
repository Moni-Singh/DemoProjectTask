package com.example.taskdemo.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
    private double rate;
    private int count;

    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }

    // Getters and Setters
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // Parcelable implementation
    protected Rating(Parcel in) {
        rate = in.readDouble();
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(rate);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
