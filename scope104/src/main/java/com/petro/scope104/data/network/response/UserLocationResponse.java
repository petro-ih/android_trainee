package com.petro.scope104.data.network.response;

import com.google.gson.annotations.SerializedName;

public class UserLocationResponse {
    @SerializedName("city")
    public String city;
    @SerializedName("country")
    public String country;
}
