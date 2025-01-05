package com.android.cryptotracker.core.presentation.util

import android.content.Context
import com.android.cryptotracker.R
import com.android.cryptotracker.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET_CONNECTION -> R.string.error_no_internet
        NetworkError.SERIALIZATION_ERROR -> R.string.error_serialization
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.UNKNOWN_ERROR -> R.string.error_unknown
    }
    return context.getString(resId)
}