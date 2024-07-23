package com.example.taskdemo.model.expandable;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    public int rating;
    public String comment;
    public String date;
    public String reviewerName;
    public String reviewerEmail;

    // Parcelable implementation
    protected Review(Parcel in) {
        rating = in.readInt();
        comment = in.readString();
        date = in.readString();
        reviewerName = in.readString();
        reviewerEmail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rating);
        dest.writeString(comment);
        dest.writeString(date);
        dest.writeString(reviewerName);
        dest.writeString(reviewerEmail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
