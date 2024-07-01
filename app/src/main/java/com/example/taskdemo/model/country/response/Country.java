package com.example.taskdemo.model.country.response;

import java.util.List;

public class Country {
    private String iso2;
    private String iso3;
    private String country;
    private List<String> cities;

    // Constructor
    public Country(String iso2, String iso3, String country, List<String> cities) {
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.country = country;
        this.cities = cities;
    }

    // Getters and Setters
    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
