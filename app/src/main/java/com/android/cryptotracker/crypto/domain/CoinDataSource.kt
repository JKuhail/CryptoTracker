package com.android.cryptotracker.crypto.domain

import com.android.cryptotracker.core.domain.util.NetworkError
import com.android.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}