package com.petro.scope104.network.response;


import com.google.gson.annotations.SerializedName;

public class UserNameResponse {
    @SerializedName("title")
    public String title;
    @SerializedName("first")
    public String first;
    @SerializedName("last")
    public String last;
}