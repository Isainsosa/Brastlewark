package com.isain.brastlewark.services.responses

import com.google.gson.annotations.SerializedName
import com.isain.brastlewark.database.entities.Inhabitant

data class CensusDataResponse(@SerializedName("Brastlewark") val inhabitants: List<Inhabitant>)