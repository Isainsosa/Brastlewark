package com.isain.network.Models

import okhttp3.ResponseBody

class HttpError : Exception {
    var statusCode: Int = 500
    var response: ResponseBody? = null

    constructor(errorMessage: String?, statusCode: Int, response: ResponseBody?): super(errorMessage){
        this.statusCode = statusCode
        this.response = response
    }
    constructor(throwable: Throwable?): super(throwable)
}