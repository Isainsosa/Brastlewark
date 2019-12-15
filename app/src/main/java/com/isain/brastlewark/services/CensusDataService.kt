package com.isain.brastlewark.services

import com.isain.brastlewark.services.responses.CensusDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface CensusDataService {
    @GET("/rrafols/mobile_test/master/data.json")
    fun retrieveCensusData(): Call<CensusDataResponse>
}