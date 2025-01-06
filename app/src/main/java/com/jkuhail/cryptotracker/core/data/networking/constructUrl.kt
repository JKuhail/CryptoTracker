package com.jkuhail.cryptotracker.core.data.networking

import com.jkuhail.cryptotracker.BuildConfig

fun constructUrl(url: String): String {
    return when {
        url.contains(com.jkuhail.cryptotracker.BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> com.jkuhail.cryptotracker.BuildConfig.BASE_URL + url.removePrefix("/")
        else -> com.jkuhail.cryptotracker.BuildConfig.BASE_URL + url
    }
}