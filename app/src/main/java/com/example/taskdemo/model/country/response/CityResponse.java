package com.example.taskdemo.model.country.response;

import java.util.ArrayList;

public class CityResponse {
    public boolean error;
    public String msg;
    public ArrayList<String> data;

    public  CityResponse (boolean error, String msg, ArrayList<String> data){
        this.error =error;
        this.data= data;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

}
