package com.fone.filmone.domain.model.common

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

fun <T, R> DataResult<T>.toMappedDataResult(transform: (T) -> R) = when (this) {
    is DataResult.EmptyData -> DataResult.EmptyData(
        if (dataFail == null) {
            null
        } else {
            DataFail(errorCode = dataFail.errorCode, message = dataFail.message)
        }
    )
    is DataResult.Fail -> DataResult.Fail(DataFail(dataFail.errorCode, dataFail.message))
    is DataResult.Success -> DataResult.Success(transform(data))

}
