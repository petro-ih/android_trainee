package com.petro.scope104.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserRegisteredResponse {
    @SerializedName("date")
    public Date date;
    @SerializedName("age")
    public String age;
}
