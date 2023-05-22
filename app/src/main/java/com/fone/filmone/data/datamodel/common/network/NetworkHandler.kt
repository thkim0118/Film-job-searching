package com.fone.filmone.data.datamodel.common.network

import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.response.exception.EmptyNetworkBodyException
import com.fone.filmone.domain.model.common.DataFail
import com.fone.filmone.domain.model.common.DataResult
import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> handleNetwork(block: suspend () -> Response<NetworkResponse<T>>): DataResult<T> {
    return try {
        LogUtil.e("block :: $block")
        block().parseNetworkResponse()
    } catch (e: EmptyNetworkBodyException) {
        DataResult.Fail(
            dataFail = DataFail(e.dataFail.errorCode, e.dataFail.message)
        )
    } catch (e: Throwable) {
        DataResult.Fail(
            dataFail = DataFail(
                errorCode = ErrorCode.ERROR_UNKNOWN,
                message = e.message ?: e.stackTraceToString()
            )
        )
    }
}

private fun <T> Response<NetworkResponse<T>>.parseNetworkResponse(): DataResult<T> {
    val response = this

    if (response.isSuccessful) {
        val networkResponse: NetworkResponse<T> =
            response.body() ?: throw EmptyNetworkBodyException(
                dataFail = DataFail(
                    errorCode = ErrorCode.ERROR_UNKNOWN,
                    message = "요청 데이터가 없습니다."
                )
            )

        return when (networkResponse.result) {
            Result.SUCCESS,
            Result.ce -> if (networkResponse.data != null) {
                DataResult.Success(data = networkResponse.data)
            } else {
                DataResult.EmptyData
            }
            Result.FAIL -> {
                DataResult.Fail(
                    dataFail = DataFail(
                        errorCode = networkResponse.errorCode ?: ErrorCode.ERROR_UNKNOWN,
                        message = networkResponse.message
                    )
                )
            }
        }
    } else {
        val errorBody = response.errorBody()?.string()
        val networkFailResponse = Gson().fromJson(errorBody, NetworkResponse::class.java)

        return DataResult.Fail(
            dataFail = DataFail(
                errorCode = networkFailResponse.errorCode ?: ErrorCode.ERROR_UNKNOWN,
                message = networkFailResponse.message
            )
        )
    }
}
