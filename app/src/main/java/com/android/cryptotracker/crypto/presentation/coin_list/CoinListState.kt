package com.android.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.android.cryptotracker.crypto.presentation.models.CoinUi

/**
 * the @Immutable annotation is used to make the CoinListState class immutable, which means that the object
 * state will never change so there's no need to recomposition.
 */
@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    var selectedCoin: CoinUi? = null
)
