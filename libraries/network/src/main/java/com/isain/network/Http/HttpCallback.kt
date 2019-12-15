package com.isain.network.Http

import com.isain.network.Models.EmptyResult
import com.isain.network.Models.HttpError
import com.isain.network.Models.Result
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class HttpCallback<T>(val callback: (response: Result<T>) -> Unit): Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        if(response != null) {
            if(response.isSuccessful) {
                response.body()?.let { callback(Result.Success(it)) } ?: callback(Result.Failure<T>(HttpError(Exception("Null Body!"))))
            } else {
                if(response.code() == 401) {
                    EventBus.getDefault().post(UnauthorizedRequestMessage())
                }
                callback(Result.Failure<T>(HttpError(response.message(), response.code(), response.errorBody())))
            }
        } else {
            callback(Result.Failure<T>(HttpError(Exception("Null Response!"))))
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        callback(Result.Failure<T>(HttpError(t)))
    }

}


internal class EmptyHttpCallback(val callback: (response: EmptyResult) -> Unit): Callback<Void> {

    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
        if(response != null) {
            if(response.isSuccessful) {
                 callback(EmptyResult.Success)
            } else {
                if(response.code() == 401) {
                    EventBus.getDefault().post(UnauthorizedRequestMessage())
                }
                callback(EmptyResult.Failure(HttpError(response.message(), response.code(), response.errorBody())))
            }
        } else {
            callback(EmptyResult.Failure(HttpError(Exception("Null Response!"))))
        }
    }

    override fun onFailure(call: Call<Void>?, t: Throwable?) {
        callback(EmptyResult.Failure(HttpError(t)))
    }

}

class UnauthorizedRequestMessage