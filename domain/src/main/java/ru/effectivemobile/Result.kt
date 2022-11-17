package ru.effectivemobile

import android.util.Log

sealed class Result<out T> {
    object Loading: Result<Nothing>()
    class Error(val error: Throwable): Result<Nothing>() {
        fun print(tag: String) {
            Log.d(tag, error.run { message + stackTraceToString() })
        }
    }
    class Success<T>(val data: T): Result<T>()
}
