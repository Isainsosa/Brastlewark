package com.isain.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.isain.network.Interceptors.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ClientBuilder {

    fun buildClient(api: Environment.Api, provider: InterceptorProvider, cache: Cache? = null): OkHttpClient {
        val interceptors = arrayListOf(
                UrlInterceptor(api, provider),
                HeaderInterceptor(provider),
                BandwidthSamplingInterceptor()
        )
        val networkInterceptors = arrayListOf<Interceptor>()

        if(BuildConfig.DEBUG) {
            val debugNetworkInterceptors = listOf(
                    StethoInterceptor(),
                    HttpLoggingInterceptor { println("\t\t $it") } .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            networkInterceptors.addAll(debugNetworkInterceptors)
        }

        return buildInternalClient(interceptors, networkInterceptors, cache)
    }

    fun externalClient(api: CustomApi, network: Network, cache: Cache? = null): OkHttpClient {

        val networkInterceptors = arrayListOf<Interceptor>()

        if(BuildConfig.DEBUG) {
            val debugNetworkInterceptors = listOf(
                    StethoInterceptor(),
                    HttpLoggingInterceptor { println("\t\t $it") } .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            networkInterceptors.addAll(debugNetworkInterceptors)
        }

        return buildInternalClient(listOf(CustomUrlInterceptor(api, network)), networkInterceptors, cache)
    }

    private fun buildInternalClient(interceptors: List<Interceptor>, networkInterceptors: List<Interceptor>, cache: Cache?): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)

        cache?.let {
            builder.cache(cache)
        }

        // Add interceptors
        interceptors.forEach {
            builder.addInterceptor(it)
        }

        // Add network interceptors (usually debug)
        networkInterceptors.forEach {
            builder.addNetworkInterceptor(it)
        }


        return builder.build()
    }
}