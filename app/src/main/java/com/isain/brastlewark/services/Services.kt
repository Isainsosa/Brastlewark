package com.isain.brastlewark.services

import com.isain.brastlewark.managers.GsonManager
import com.isain.network.Api
import retrofit2.converter.gson.GsonConverterFactory

object Services {
    val main = Api.main(listOf(GsonConverterFactory.create(GsonManager.defaultGson())))

    val censusDataService = main.create(CensusDataService::class.java)
}