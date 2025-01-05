package com.android.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cryptotracker.core.domain.util.onError
import com.android.cryptotracker.core.domain.util.onSuccess
import com.android.cryptotracker.crypto.domain.CoinDataSource
import com.android.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() } // load coins when the UI subscribe to the state flow.
        .stateIn( // expose only the immutable state flow to the UI.
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // stop collecting the state flow when the UI is not visible.
            initialValue = CoinListState()
        )

    private fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.CoinClicked -> {}
        }

    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() })
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}