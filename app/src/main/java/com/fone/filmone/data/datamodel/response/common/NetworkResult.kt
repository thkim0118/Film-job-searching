package com.fone.filmone.data.datamodel.response.common

sealed class NetworkResult<out T> {
    data class Success<T>(
        val data: T
    ) : NetworkResult<T>()

    data class EmptyData(
        val networkFail: NetworkFail?
    ) : NetworkResult<Nothing>()

    data class Fail(
        val networkFail: NetworkFail
    ) : NetworkResult<Nothing>()
}