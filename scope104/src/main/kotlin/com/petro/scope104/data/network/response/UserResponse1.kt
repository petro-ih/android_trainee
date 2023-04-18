package com.petro.scope104.data.network.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("gender")
    var gender: String,

    @SerializedName("name")
    var name: UserNameResponse,

    @SerializedName("dob")
    var dob: UserDobResponse,

    @SerializedName("picture")
    var picture: UserPictureResponse,

    @SerializedName("location")
    var location: UserLocationResponse,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("login")
    var login: UserLoginResponse,

    @SerializedName("email")
    var email: String,

    @SerializedName("nat")
    var nat: String,

    @SerializedName("registered")
    var registered: UserRegisteredResponse
)