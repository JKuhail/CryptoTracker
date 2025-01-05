package com.android.cryptotracker.core.data.networking

import com.android.cryptotracker.BuildConfig

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.removePrefix("/")
        else -> BuildConfig.BASE_URL + url
    }
}