package com.example.taskdemo.model.country.response;

import java.util.ArrayList;

public class StateData {
    public String name;
    public String iso3;
    public String iso2;
    public ArrayList<State> states;

    public  StateData(String name, String iso3,String iso2, ArrayList<State> states){
        this.name = name;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.states = states;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso3() {
        return iso3;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<State> getStates() {
        return states;
    }
}
