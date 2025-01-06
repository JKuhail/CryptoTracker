package com.jkuhail.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jkuhail.cryptotracker.core.presentation.util.ObserveAsEvents
import com.jkuhail.cryptotracker.core.presentation.util.toString
import com.jkuhail.cryptotracker.crypto.presentation.coin_details.CoinDetailsScreen
import com.jkuhail.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.jkuhail.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.jkuhail.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.jkuhail.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when (event) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    /**
                     * @param onAction -> The :: operator is used to create a member reference
                     * which is a way to refer to a function or property of a class without
                     * actually calling it. It's like a pointer to the function or property.
                     */
                    /*CoinListScreen(
                        state = state.value,
                        modifier = Modifier.padding(innerPadding),
                        onAction = viewModel::onAction
                    )*/
                    when {
                        state.selectedCoin != null -> {
                            CoinDetailsScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        else -> {
                            CoinListScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding),
                                onAction = viewModel::onAction
                            )
                        }
                    }
                }
            }
        }
    }
}
