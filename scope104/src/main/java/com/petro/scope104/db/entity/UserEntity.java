package com.petro.scope104.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;

@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey
    @NonNull
    public String username;
    @ColumnInfo
    public String avatarUrl;
    @ColumnInfo
    public String avatarUrlXXL;
    @ColumnInfo
    public String firstName;
    @ColumnInfo
    public String lastName;
    @ColumnInfo
    public Date dob;
    @ColumnInfo
    public int age;
    @ColumnInfo
    public String nat;
    @ColumnInfo
    public String phone;
    @ColumnInfo
    public String email;
    @ColumnInfo
    public Date registered;
    @ColumnInfo
    public Boolean isMale;


    public UserEntity(){}


    public UserEntity(@NonNull String username, String avatarUrl, String avatarUrlXXL, String firstName, String lastName, Date dob, int age, String nat, String phone, String email, Date registered, Boolean isMale) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.avatarUrlXXL = avatarUrlXXL;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.age = age;
        this.nat = nat;
        this.phone = phone;
        this.email = email;
        this.registered = registered;
        this.isMale = isMale;
    }
}
