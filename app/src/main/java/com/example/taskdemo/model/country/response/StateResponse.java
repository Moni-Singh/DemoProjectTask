package com.example.taskdemo.model.country.response;

public class StateResponse {
    public boolean error;
    public String msg;
    public StateData data;

    public  StateResponse (boolean error, String msg, StateData stateData){
        this.error = error;
        this.data = stateData;
        this.msg = msg;
    }

    public StateData getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setData(StateData data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
