package com.petro.scope104.network.response;


import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("gender")
    public String gender;
    @SerializedName("name")
    public UserNameResponse name;
    @SerializedName("dob")
    public UserDobResponse dob;
    @SerializedName("picture")
    public UserPictureResponse picture;
    @SerializedName("location")
    public UserLocationResponse location;
    @SerializedName("phone")
    public String phone;
    @SerializedName("login")
    public UserLoginResponse login;
    @SerializedName("email")
    public String email;
    @SerializedName("nat")
    public String nat;
    @SerializedName("registered")
    public UserRegisteredResponse registered;
}