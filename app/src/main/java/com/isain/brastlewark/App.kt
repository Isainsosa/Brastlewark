package com.isain.brastlewark

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.isain.brastlewark.database.BrastlewarkDatabase

class App: Application() {

    companion object {
        lateinit var appContext: Context
        val database by lazy {
            Room.databaseBuilder(appContext, BrastlewarkDatabase::class.java, "brastlewark.database")
                .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

    }
}