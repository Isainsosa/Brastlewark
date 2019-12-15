package com.isain.network.Extensions

import com.isain.network.Http.EmptyHttpCallback
import com.isain.network.Http.HttpCallback
import com.isain.network.Http.UnauthorizedRequestMessage
import com.isain.network.Models.EmptyResult
import com.isain.network.Models.HttpError
import com.isain.network.Models.Result
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Response

fun <T> Call<T>.enqueue(callback: (response: Result<T>) -> Unit) {
    this.enqueue(HttpCallback<T>(callback))
}

fun Call<Void>.enqueueEmptyResult(callback: (response: EmptyResult) -> Unit) {
    this.enqueue(EmptyHttpCallback(callback))
}

//Only call this from a worker thread
fun <T> Call<T>.executeCall(): Result<T> {
    try {
        return execute().handleCall()
    } catch (e: Exception) {
        return Result.Failure<T>(HttpError(e))
    }
}

//Only call this from a worker thread
fun Call<Void>.executeEmptyCall(): EmptyResult {
    try {
        return execute().handleEmptyCall()
    } catch (e: Exception) {
        return EmptyResult.Failure(HttpError(e))
    }
}

private fun <T> Response<T>?.handleCall(): Result<T> {
    return if (this != null) {
        if (isSuccessful) {
            body()?.let { Result.Success<T>(it) } ?: throw Exception("Null Body!")
        } else {
            if (code() == 401) {
                EventBus.getDefault().post(UnauthorizedRequestMessage())
            }
            Result.Failure<T>(HttpError(message(), code(), errorBody()))
        }
    } else {
        Result.Failure<T>(HttpError(Exception("Null Response!")))
    }
}


private fun <T> Response<T>?.handleEmptyCall(): EmptyResult {
    return if (this != null) {
        if (isSuccessful) {
            EmptyResult.Success
        } else {
            if (code() == 401) {
                EventBus.getDefault().post(UnauthorizedRequestMessage())
            }
            EmptyResult.Failure(HttpError(message(), code(), errorBody()))
        }
    } else {
        EmptyResult.Failure(HttpError(Exception("Null Response!")))
    }
}


