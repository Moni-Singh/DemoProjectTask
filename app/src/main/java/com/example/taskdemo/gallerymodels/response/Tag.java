package com.example.taskdemo.gallerymodels.response;

public class Tag {
    private String type;
    private String title;

    public Tag(String type,String title) {

        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

