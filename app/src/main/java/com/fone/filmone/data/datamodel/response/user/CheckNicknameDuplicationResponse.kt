package com.fone.filmone.data.datamodel.response.user

import com.google.gson.annotations.SerializedName

data class CheckNicknameDuplicationResponse(
    @SerializedName("isDuplicate")
    val isDuplicate: Boolean,
    @SerializedName("nickname")
    val nickname: String
)
