package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserNameResponse (
    @SerializedName("title")
    val title: String,

    @SerializedName("first")
    val first: String,

    @SerializedName("last")
    val last: String,
)