package com.isain.network.Models

sealed class Result<T> {
    class Success<T>(val value: T) : Result<T>()
    class Failure<T>(val exception: HttpError) : Result<T>()
}

sealed class EmptyResult {
    object Success : EmptyResult()
    class Failure(val exception: HttpError) : EmptyResult()
}