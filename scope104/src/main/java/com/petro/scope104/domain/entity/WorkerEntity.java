package com.petro.scope104.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class WorkerEntity implements Serializable {
    private String avatarUrl;
    private final String avatarUrlXXL;
    private String firstName;
    private String lastName;
    private Date dob;
    private final int age;
    private String city;
    private String country;
    private final String phone;
    private final String username;
    private final String email;
    private final String nat;
    private final Date registered;
    private final boolean isMale;

    public WorkerEntity(String avatarUrl, String avatarUrlXXL, String firstName, String lastName, Date dob, int age, String city, String country, String phone, String username, String email, String nat, Date registered, boolean isMale) {
        this.avatarUrl = avatarUrl;
        this.avatarUrlXXL = avatarUrlXXL;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.age = age;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.nat = nat;
        this.registered = registered;
        this.isMale = isMale;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getNatCountry(){
        return new Locale("", getNat()).getDisplayCountry();
    }

    public String getUsername() {
        return username;
    }

    public Date getRegistered() {
        return registered;
    }

    public boolean isMale() {
        return isMale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerEntity workerEntity = (WorkerEntity) o;
        return age == workerEntity.age && Objects.equals(avatarUrl, workerEntity.avatarUrl) && Objects.equals(avatarUrlXXL, workerEntity.avatarUrlXXL) && firstName.equals(workerEntity.firstName) && lastName.equals(workerEntity.lastName) && Objects.equals(dob, workerEntity.dob) && Objects.equals(city, workerEntity.city) && Objects.equals(country, workerEntity.country) && Objects.equals(phone, workerEntity.phone) && username.equals(workerEntity.username) && Objects.equals(email, workerEntity.email) && Objects.equals(nat, workerEntity.nat) && Objects.equals(registered, workerEntity.registered) && isMale == workerEntity.isMale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatarUrl, avatarUrlXXL, firstName, lastName, dob, age, city, country, phone, username, email, nat, registered, isMale);
    }
}
