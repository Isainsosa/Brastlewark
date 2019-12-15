package com.isain.network

import retrofit2.Converter
import retrofit2.Retrofit

object Api {
    val network = Network()

    private val mainClient = ClientBuilder.buildClient(Environment.Api.Main, network)

    fun main(converters: List<Converter.Factory>): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(network.environment.mainUrl)
                .client(mainClient)
        converters.forEach {
            builder.addConverterFactory(it)
        }
        return builder.build()
    }

    fun custom(customApi: CustomApi, converters: List<Converter.Factory>): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(customApi.provideUrl(network))
                .client(ClientBuilder.externalClient(customApi, network))
        converters.forEach {
            builder.addConverterFactory(it)
        }
        return builder.build()
    }
}