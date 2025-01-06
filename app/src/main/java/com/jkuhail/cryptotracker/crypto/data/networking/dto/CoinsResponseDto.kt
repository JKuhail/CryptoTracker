package com.jkuhail.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto (
    val data: List<com.jkuhail.cryptotracker.crypto.data.networking.dto.CoinDto>
)