package com.example.taskdemo.model.response;

public class Rating {
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
};
