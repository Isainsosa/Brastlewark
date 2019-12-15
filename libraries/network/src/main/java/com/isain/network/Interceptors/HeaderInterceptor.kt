package com.isain.network.Interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(val provider: InterceptorProvider) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestInfo = provider.provideRequestInfo()
        val request = chain.request()
        val builder = request.newBuilder().also {
            if (request.header("Accept") == null)
                it.addHeader("Accept", requestInfo.acceptHeader)

            it.addHeader("User-Agent", requestInfo.userAgent)
        }
        return chain.proceed(builder.build())
    }


}