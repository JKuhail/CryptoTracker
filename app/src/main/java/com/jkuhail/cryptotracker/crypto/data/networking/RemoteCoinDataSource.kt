package com.jkuhail.cryptotracker.crypto.data.networking

import com.jkuhail.cryptotracker.core.data.networking.constructUrl
import com.jkuhail.cryptotracker.core.data.networking.safeCall
import com.jkuhail.cryptotracker.core.domain.util.NetworkError
import com.jkuhail.cryptotracker.core.domain.util.Result
import com.jkuhail.cryptotracker.core.domain.util.map
import com.jkuhail.cryptotracker.crypto.data.mappers.toCoin
import com.jkuhail.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.jkuhail.cryptotracker.crypto.domain.Coin
import com.jkuhail.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<com.jkuhail.cryptotracker.crypto.data.networking.dto.CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response -> response.data.map { it.toCoin() } }
    }
}