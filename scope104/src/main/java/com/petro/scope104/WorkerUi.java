package com.petro.scope104;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class WorkerUi implements Serializable {
    private String avatarUrl, avatarUrlXXL;
    private String name;
    private Date dob;
    private int age;
    private String city;
    private String phone;
    private String username;
    private String email;
    private String nat;
    private Date registered;

    public WorkerUi(String avatarUrl, String avatarUrlXXL, String name, Date dob, String city, int age, String phone, String username, String email, String nat, Date registered) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.dob = dob;
        this.city = city;
        this.age = age;
        this.avatarUrlXXL = avatarUrlXXL;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.nat = nat;
        this.registered = registered;
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

    public int getAge() {
        return age;
    }

    public String getAvatarUrlXXL() {
        return avatarUrlXXL;
    }
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNat() {
        return nat;
    }

    public String getUsername() {
        return username;
    }

    public Date getRegistered() {
        return registered;
    }
}
