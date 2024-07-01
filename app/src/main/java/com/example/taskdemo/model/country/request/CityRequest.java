package com.example.taskdemo.model.country.request;

public class CityRequest {
    public String country;
    public String state;


    public  CityRequest(String country, String state){
         this.country = country;
         this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
