package com.android.cryptotracker.crypto.data.networking

import com.android.cryptotracker.core.data.networking.constructUrl
import com.android.cryptotracker.core.data.networking.safeCall
import com.android.cryptotracker.core.domain.util.NetworkError
import com.android.cryptotracker.core.domain.util.Result
import com.android.cryptotracker.core.domain.util.map
import com.android.cryptotracker.crypto.data.mappers.toCoin
import com.android.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.android.cryptotracker.crypto.domain.Coin
import com.android.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response -> response.data.map { it.toCoin() } }
    }
}