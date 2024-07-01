package com.example.taskdemo.gallerymodels.response;

import com.google.gson.annotations.SerializedName;

public class TopicSubmission {
    private String status;
    @SerializedName("approved_on")
    private String approvedOn;
    public TopicSubmission(String status, String approvedOn) {

        this.status = status;
        this.approvedOn = approvedOn;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(String approvedOn) {
        this.approvedOn = approvedOn;
    }
}