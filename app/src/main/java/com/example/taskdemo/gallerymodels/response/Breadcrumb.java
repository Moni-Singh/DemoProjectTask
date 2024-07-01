package com.example.taskdemo.gallerymodels.response;

public class Breadcrumb {
    private String slug;
    private String title;
    private int index;
    private String type;

    public Breadcrumb(String slug, String title, int index, String type) {
        this.slug = slug;
        this.title = title;
        this.index = index;
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
