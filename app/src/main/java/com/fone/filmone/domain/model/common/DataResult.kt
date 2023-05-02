package com.fone.filmone.domain.model.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class DataResult<out T> {
    data class Success<T>(
        val data: T
    ) : DataResult<T>()

    object EmptyData : DataResult<Nothing>()

    data class Fail(
        val dataFail: DataFail
    ) : DataResult<Nothing>()
}

suspend fun <T> DataResult<T>.onSuccess(
    dispatcher: CoroutineDispatcher,
    action: suspend (value: T?) -> Unit
): DataResult<T> {
    if (this is DataResult.Success) {
        withContext(dispatcher) {
            action(data)
        }
    }

    if (this is DataResult.EmptyData) {
        withContext(dispatcher) {
            action(null)
        }
    }

    return this
}

fun <T> DataResult<T>.onSuccess(
    onSuccess: (value: T?) -> Unit,
): DataResult<T> {
    if (this is DataResult.Success) {
        onSuccess(data)
    }

    if (this is DataResult.EmptyData) {
        onSuccess(null)
    }

    return this
}

fun <T> DataResult<T>.onFail(action: (dataFail: DataFail) -> Unit): DataResult<T> {
    if (this is DataResult.Fail) {
        action(dataFail)
    }
    return this
}

fun <T> DataResult<T>.isSuccess(): Boolean {
    return when (this) {
        is DataResult.Success -> true
        else -> false
    }
}

fun <T> DataResult<T>.isFail(): Boolean {
    return when (this) {
        is DataResult.Fail -> true
        else -> false
    }
}

fun <T> DataResult<T>.isEmptyData(): Boolean {
    return when (this) {
        is DataResult.EmptyData -> true
        else -> false
    }
}

fun <T> DataResult<T>.getOrElse(action: () -> T): T {
    return when (this) {
        is DataResult.Success -> this.data
        else -> action()
    }
}

fun <T> DataResult<T>.getOrNull(): T? {
    return when (this) {
        is DataResult.Success -> this.data
        else -> null
    }
}


fun <T, R> DataResult<T>.toMappedDataResult(transform: (T) -> R) = when (this) {
    is DataResult.EmptyData -> DataResult.EmptyData
    is DataResult.Fail -> DataResult.Fail(DataFail(dataFail.errorCode, dataFail.message))
    is DataResult.Success -> DataResult.Success(transform(data))

}
