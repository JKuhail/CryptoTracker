package com.jkuhail.cryptotracker.crypto.domain

import com.jkuhail.cryptotracker.core.domain.util.NetworkError
import com.jkuhail.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}