package com.fone.filmone.data.datamodel.common.network

import com.google.gson.annotations.SerializedName

data class NetworkResponse<T>(
    @SerializedName("result")
    val result: Result,
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String,
    @SerializedName("errorCode")
    val errorCode: String?
)