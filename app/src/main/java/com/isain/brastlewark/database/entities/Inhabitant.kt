package com.isain.brastlewark.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inhabitant(@PrimaryKey val id: Long = 0L,
                      val name: String = "",
                      val thumbnail: String = "",
                      val age: Int = -1,
                      val weight: Double = 0.0,
                      val height: Double = 0.0,
                      val hair_color: String = ""
)