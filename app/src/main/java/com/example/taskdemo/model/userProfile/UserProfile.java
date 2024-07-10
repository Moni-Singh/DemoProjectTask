package com.example.taskdemo.model.userProfile;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    private String name;
    private String email;
    private String address;
    private String profileImage;
    private String backgroundImage;
    private String phoneNo;
    private String bio;

    public UserProfile(String name, String email, String address, String profileImage, String backgroundImage, String phoneNo, String bio) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.phoneNo = phoneNo;
        this.bio = bio;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Parcelable implementation
    protected UserProfile(Parcel in) {
        name = in.readString();
        email = in.readString();
        address = in.readString();
        profileImage = in.readString();
        backgroundImage = in.readString();
        phoneNo = in.readString();
        bio = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(profileImage);
        dest.writeString(backgroundImage);
        dest.writeString(phoneNo);
        dest.writeString(bio);
    }
}
