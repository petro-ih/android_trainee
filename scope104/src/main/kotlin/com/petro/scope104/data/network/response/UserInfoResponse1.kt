package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserInfoResponse {
    @SerializedName("seed")
    var seed: String? = null

    @SerializedName("results")
    var results = 0

    @SerializedName("page")
    var page = 0
}