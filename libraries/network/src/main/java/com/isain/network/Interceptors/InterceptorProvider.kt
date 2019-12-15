package com.isain.network.Interceptors

import com.isain.network.Environment
import com.isain.network.Models.RequestInfo

interface InterceptorProvider {
    fun provideRequestInfo(): RequestInfo
    fun provideUrl(api: Environment.Api): String
}