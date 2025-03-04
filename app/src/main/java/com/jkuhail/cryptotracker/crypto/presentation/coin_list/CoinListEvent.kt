package com.jkuhail.cryptotracker.crypto.presentation.coin_list

import com.jkuhail.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}