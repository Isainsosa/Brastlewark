package com.isain.brastlewark.services.responses

import com.google.gson.annotations.SerializedName

data class CensusDataResponse(@SerializedName("Brastlewark") val inhabitants: List<Inhabitant>)

data class Inhabitant(val id: Long,
                      val name: String,
                      val thumbnail: String,
                      val age: Int,
                      val weight: Double,
                      val height: Double,
                      val hair_color: String,
                      val professions: List<String>,
                      val friends: List<String>
)