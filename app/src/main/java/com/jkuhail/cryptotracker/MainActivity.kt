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
import com.jkuhail.cryptotracker.core.navigation.AdaptiveCoinListDetailsPane
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
                    AdaptiveCoinListDetailsPane(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
