package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

class UserDobResponse (
    @SerializedName("date")
    val date: Date,

    @SerializedName("age")
    val age: Int
)