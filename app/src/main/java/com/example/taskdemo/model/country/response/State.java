package com.example.taskdemo.model.country.response;

public class State {
    public String name;
    public String state_code;

    public  State (String name, String state_code){
        this.name = name;
         this.state_code = state_code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getState_code() {
        return state_code;
    }
}
