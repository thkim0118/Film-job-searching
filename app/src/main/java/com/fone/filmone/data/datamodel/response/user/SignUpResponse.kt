package com.fone.filmone.data.datamodel.response.user

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("user")
    val userResponse: UserResponse
)