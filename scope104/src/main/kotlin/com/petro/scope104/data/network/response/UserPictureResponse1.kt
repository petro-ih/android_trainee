package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserPictureResponse(
    @SerializedName("large")
    val large: String,

    @SerializedName("medium")
    val medium: String,

    @SerializedName("thumbnail")
    val thumbnail: String,
)