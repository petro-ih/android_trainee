package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserLoginResponse (
    @SerializedName("username")
    val username: String
)