package com.isain.brastlewark.managers

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.isain.brastlewark.App

object ImageLoaderManager {

    fun loadImage(from: String, view: ImageView) {
        Glide.with(App.appContext)
            .load(from)
            .into(view)
    }
}