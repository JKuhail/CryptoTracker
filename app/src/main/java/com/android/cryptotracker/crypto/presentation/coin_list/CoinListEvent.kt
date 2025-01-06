package com.android.cryptotracker.crypto.presentation.coin_list

import com.android.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}