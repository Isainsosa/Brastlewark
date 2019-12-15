package com.isain.network

import com.isain.network.Interceptors.InterceptorProvider
import com.isain.network.Models.RequestInfo

class Network(
        private val requestInfo: RequestInfo = RequestInfo(),
        var environment: Environment = Environment.Production()
) : InterceptorProvider {

    fun updateUserAgent(userAgent: String) {
        requestInfo.userAgent = userAgent
    }

    override fun provideRequestInfo() = requestInfo
    override fun provideUrl(api: Environment.Api) = environment.url(api)

}