package com.example.taskdemo.gallerymodels.response;

import com.google.gson.annotations.SerializedName;

public class Urls {
    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;
    @SerializedName("small_s3")
    private String smallS3;

    public Urls(String raw, String full, String regular, String small, String thumb, String smallS3) {
        this.raw = raw;
        this.full = full;
        this.regular = regular;
        this.small = small;
        this.thumb = thumb;
        this.smallS3 = smallS3;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSmallS3() {
        return smallS3;
    }

    public void setSmallS3(String smallS3) {
        this.smallS3 = smallS3;
    }
}
