package com.example.taskdemo.gallerymodels.response;

import java.util.List;

public class GalleryDataResponse {
    private int total;
    private int total_pages;
    private List<GalleryData> results;

    // Getters and setters
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int totalPages) {
        this.total_pages = totalPages;
    }

    public List<GalleryData> getResults() {
        return results;
    }

    public void setResults(List<GalleryData> results) {
        this.results = results;
    }
}
