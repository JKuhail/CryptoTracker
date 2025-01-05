package com.android.cryptotracker.crypto.presentation.coin_list

import com.android.cryptotracker.crypto.presentation.models.CoinUi

/**
 * The action interfaces stands for the I letter in MVI architecture which stands for
 * Intend. It just bundles all the actions that can be performed in a single screen.
 * For our screen here, we only have one action which is clicking the coin, but we could also
 * add an action for refreshing the screen for example.
 */
sealed interface CoinListAction {
    data class CoinClicked(val coin: CoinUi) : CoinListAction
    // data object OnRefresh : CoinListAction
}