package com.android.cryptotracker.crypto.data.mappers

import com.android.cryptotracker.crypto.data.networking.dto.CoinDto
import com.android.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin() = Coin(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr
)