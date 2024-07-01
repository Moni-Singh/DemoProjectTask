package com.example.taskdemo.model.country.response;

import java.util.List;
public class CountryDataResponse {
    private boolean error;
    private String msg;
    private List<Country> data;

    // Constructor
    public CountryDataResponse(boolean error, String msg, List<Country> data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    // Getters and Setters
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }
}