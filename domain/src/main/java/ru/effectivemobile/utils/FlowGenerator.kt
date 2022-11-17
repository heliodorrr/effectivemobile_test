package ru.effectivemobile.utils

import kotlinx.coroutines.flow.flow
import ru.effectivemobile.Result

inline fun <T> generateFlow(
    crossinline valueProducer: suspend ()->T
) = flow {
    emit(Result.Loading)
    try {
        emit(Result.Success(valueProducer()))
    } catch (e: Throwable) {
        emit(Result.Error(e))
    }
}