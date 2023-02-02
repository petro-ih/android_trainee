package com.petro.scope104.network.response;


import com.google.gson.annotations.SerializedName;

public class UserPictureResponse {
    @SerializedName("large")
    public String large;
    @SerializedName("medium")
    public String medium;
    @SerializedName("thumbnail")
    public String thumbnail;
}