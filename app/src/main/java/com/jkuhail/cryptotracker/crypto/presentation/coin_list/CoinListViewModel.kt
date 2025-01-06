package com.jkuhail.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkuhail.cryptotracker.core.domain.util.onError
import com.jkuhail.cryptotracker.core.domain.util.onSuccess
import com.jkuhail.cryptotracker.crypto.domain.CoinDataSource
import com.jkuhail.cryptotracker.crypto.presentation.models.CoinUi
import com.jkuhail.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    /**
     * @param _state -> Use StateFlow when you want to represent and observe state changes in a
     * reactive way, especially when you want to share the state among multiple consumers and
     * automatically update them when the state changes.
     *
     * The current cashed value will be re-emitted when a new subscriber is added, like when
     * the screen rotation changes.
     */
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() } // load coins when the UI subscribe to the state flow.
        .stateIn( // expose only the immutable state flow to the UI.
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // stop collecting the state flow when the UI is not visible.
            initialValue = CoinListState()
        )

    /**
     * @param _events -> Use channels when you need to send and receive data asynchronously between
     * coroutines, especially when you want to ensure that each value is consumed by only one
     * receiver. This is the desired behavior in case we want to show an error popup, since
     * the value will not be cashed nor re-emitted on a screen rotation for example.
     */
    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.CoinClicked -> {
                selectCoin(action.coin)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    println(history)
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }
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
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}