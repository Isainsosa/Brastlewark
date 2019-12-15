package com.isain.network.Interceptors

import com.isain.network.CustomApi
import com.isain.network.Environment
import com.isain.network.Network
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UrlInterceptor(private val api: Environment.Api, private val provider: InterceptorProvider): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlToInject = provider.provideUrl(api)
        val urlObjToInject = HttpUrl.parse(urlToInject)
        val request = chain.request()
        val newUrl = request.url().newBuilder()
                .host(urlObjToInject?.host() ?: throw Exception("Invalid URL"))
                .build()

        val builder = request.newBuilder()
        builder.url(newUrl)
        return chain.proceed(builder.build())
    }


}

class CustomUrlInterceptor(private val api: CustomApi, private val network: Network): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlToInject = api.provideUrl(network)
        val urlObjToInject = HttpUrl.parse(urlToInject)
        val request = chain.request()
        val newUrl = request.url().newBuilder()
                .host(urlObjToInject?.host() ?: throw Exception("Invalid URL"))
                .build()

        val builder = request.newBuilder()
        builder.url(newUrl)
        return chain.proceed(builder.build())
    }


}