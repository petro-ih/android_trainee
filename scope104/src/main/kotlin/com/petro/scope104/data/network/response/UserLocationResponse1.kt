package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserLocationResponse (
    @SerializedName("city")
    val city: String,

    @SerializedName("country")
    val country: String
)