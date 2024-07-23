package com.example.taskdemo.model.expandable;

public class ProductCategoryResponse {
    public String slug;
    public String name;
    public String url;

    public ProductCategoryResponse(String slug, String name, String url){
        this.slug = slug;
        this.name = name;
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }
}
