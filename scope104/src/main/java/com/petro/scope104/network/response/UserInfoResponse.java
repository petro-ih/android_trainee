package com.petro.scope104.network.response;


import com.google.gson.annotations.SerializedName;

public class UserInfoResponse{
    @SerializedName("seed")
    public String seed;
    @SerializedName("results")
    public int results;
    @SerializedName("page")
    public int page;
}