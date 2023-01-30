package com.petro.scope104;

import java.util.Date;

public class WorkerUi {
    private String avatarUrl;
    private String name;
    private Date dob;
    private String city;

    public WorkerUi(String avatarUrl, String name, Date dob, String city) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.dob = dob;
        this.city = city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
