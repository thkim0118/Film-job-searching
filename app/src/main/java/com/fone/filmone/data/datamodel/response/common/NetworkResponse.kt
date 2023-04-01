package com.fone.filmone.data.datamodel.response.common

data class NetworkResponse<T>(
    @Serializ
    val result: String,
    val data: T?,
    val message: String,
    val errorCode: String?
)