package com.isain.network.Interceptors

import com.facebook.network.connectionclass.DeviceBandwidthSampler
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BandwidthSamplingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        DeviceBandwidthSampler.getInstance().startSampling()
        val response = chain.proceed(request)
        DeviceBandwidthSampler.getInstance().stopSampling()
        return response
    }
}