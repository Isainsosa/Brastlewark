package com.isain.brastlewark.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isain.brastlewark.database.dao.InhabitantDao
import com.isain.brastlewark.database.entities.Inhabitant

@Database(entities = arrayOf(Inhabitant::class), version = 1)
abstract class BrastlewarkDatabase: RoomDatabase() {
    abstract fun inhabitantDao(): InhabitantDao
}