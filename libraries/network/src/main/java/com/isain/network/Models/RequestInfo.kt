package com.isain.network.Models

import android.os.Build

data class RequestInfo(
        var userAgent: String = "Android: (" + Build.MODEL + "; Android " + Build.VERSION.SDK_INT + ";)",
        val acceptHeader: String = "application/json"
)