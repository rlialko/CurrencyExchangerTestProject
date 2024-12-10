package com.ruslanlialko.currencyexchanger.domain.use_case.base

sealed class Outcome<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Loading<T> : Outcome<T>()
    class Success<T>(data: T) : Outcome<T>(data)
    class Error<T>(error: Throwable, data: T? = null) : Outcome<T>(data, error)
}