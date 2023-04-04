package com.fone.filmone.data.datamodel.response.common

import com.fone.filmone.data.datamodel.response.exception.EmptyNetworkBodyException
import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> handleNetwork(block: suspend () -> Response<NetworkResponse<T>>): NetworkResult<T> {
    return try {
        block.invoke().parseNetworkResponse()
    } catch (e: EmptyNetworkBodyException) {
        NetworkResult.Fail(networkFail = e.networkFail)
    } catch (e: Throwable) {
        NetworkResult.Fail(
            networkFail = NetworkFail(
                errorCode = ErrorCode.ERROR_UNKNOWN,
                message = e.message ?: e.stackTraceToString()
            )
        )
    }
}

private fun <T> Response<NetworkResponse<T>>.parseNetworkResponse(): NetworkResult<T> {
    val response = this

    if (response.isSuccessful) {
        val networkResponse: NetworkResponse<T> =
            response.body() ?: throw EmptyNetworkBodyException(
                networkFail = NetworkFail(
                    errorCode = ErrorCode.ERROR_UNKNOWN,
                    message = "요청 데이터가 없습니다."
                )
            )

        return when (networkResponse.result) {
            Result.SUCCESS,
            Result.ce -> if (networkResponse.data != null) {
                NetworkResult.Success(data = networkResponse.data)
            } else {
                NetworkResult.EmptyData(
                    networkFail = if (networkResponse.errorCode == null) {
                        null
                    } else {
                        NetworkFail(
                            errorCode = networkResponse.errorCode ?: ErrorCode.ERROR_UNKNOWN,
                            message = networkResponse.message
                        )
                    }
                )
            }
            Result.FAIL -> {
                NetworkResult.Fail(
                    networkFail = NetworkFail(
                        errorCode = networkResponse.errorCode ?: ErrorCode.ERROR_UNKNOWN,
                        message = networkResponse.message
                    )
                )
            }
        }
    } else {
        val errorBody = response.errorBody()?.string()
        val networkFailResponse = Gson().fromJson(errorBody, NetworkResponse::class.java)

        return NetworkResult.Fail(
            networkFail = NetworkFail(
                errorCode = networkFailResponse.errorCode ?: ErrorCode.ERROR_UNKNOWN,
                message = networkFailResponse.message
            )
        )
    }
}
