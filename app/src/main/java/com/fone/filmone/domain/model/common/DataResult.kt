package com.fone.filmone.domain.model.common

import com.fone.filmone.data.datamodel.response.common.NetworkResult

sealed class DataResult<out T> {
    data class Success<T>(
        val data: T
    ) : DataResult<T>()

    data class EmptyData(
        val dataFail: DataFail?
    ) : DataResult<Nothing>()

    data class Fail(
        val dataFail: DataFail
    ) : DataResult<Nothing>()
}

fun <T> DataResult<T>.onSuccess(action: (value: T) -> Unit): DataResult<T> {
    if (this is DataResult.Success) {
        action(data)
    }
    return this
}

fun <T> DataResult<T>.onFail(action: (dataFail: DataFail) -> Unit): DataResult<T> {
    if (this is DataResult.Fail) {
        action(dataFail)
    }
    return this
}

fun <T> NetworkResult<T>.toDataResult() = when (this) {
    is NetworkResult.EmptyData -> DataResult.EmptyData(
        if (networkFail == null) {
            null
        } else {
            DataFail(errorCode = networkFail.errorCode, message = networkFail.message)
        }
    )
    is NetworkResult.Fail -> DataResult.Fail(DataFail(networkFail.errorCode, networkFail.message))
    is NetworkResult.Success -> DataResult.Success(data)
}

fun <T, R> NetworkResult<T>.toMappedDataResult(transform: (T) -> R) = when (this) {
    is NetworkResult.EmptyData -> DataResult.EmptyData(
        if (networkFail == null) {
            null
        } else {
            DataFail(errorCode = networkFail.errorCode, message = networkFail.message)
        }
    )
    is NetworkResult.Fail -> DataResult.Fail(DataFail(networkFail.errorCode, networkFail.message))
    is NetworkResult.Success -> DataResult.Success(transform(data))

}
