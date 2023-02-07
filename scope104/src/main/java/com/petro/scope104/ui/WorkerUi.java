package com.petro.scope104.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class WorkerUi implements Serializable {
    private String avatarUrl;
    private final String avatarUrlXXL;
    private String name;
    private Date dob;
    private final int age;
    private String city;
    private final String phone;
    private final String username;
    private final String email;
    private final String nat;
    private final Date registered;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerUi workerUi = (WorkerUi) o;
        return age == workerUi.age && Objects.equals(avatarUrl, workerUi.avatarUrl) && Objects.equals(avatarUrlXXL, workerUi.avatarUrlXXL) && name.equals(workerUi.name) && Objects.equals(dob, workerUi.dob) && Objects.equals(city, workerUi.city) && Objects.equals(phone, workerUi.phone) && username.equals(workerUi.username) && Objects.equals(email, workerUi.email) && Objects.equals(nat, workerUi.nat) && Objects.equals(registered, workerUi.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatarUrl, avatarUrlXXL, name, dob, age, city, phone, username, email, nat, registered);
    }
}
