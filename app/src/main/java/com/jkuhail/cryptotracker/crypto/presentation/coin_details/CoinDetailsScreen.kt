@file:OptIn(ExperimentalLayoutApi::class)

package com.jkuhail.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jkuhail.cryptotracker.R
import com.jkuhail.cryptotracker.crypto.presentation.coin_details.components.InfoCard
import com.jkuhail.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.jkuhail.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.jkuhail.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.jkuhail.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinDetailsScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        state.selectedCoin?.let { coinUi ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(coinUi.icon),
                    contentDescription = coinUi.name,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = coinUi.name,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = coinUi.symbol,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfoCard(
                        title = stringResource(R.string.market_cap),
                        formattedText = "$${coinUi.marketCapUsd.formatted}",
                        icon = ImageVector.vectorResource(R.drawable.stock)
                    )
                    InfoCard(
                        title = stringResource(R.string.price),
                        formattedText = "$${coinUi.priceUsd.formatted}",
                        icon = ImageVector.vectorResource(R.drawable.dollar)
                    )
                    val absoluteChangeFormatted =
                        (coinUi.priceUsd.value * (coinUi.changePercent24Hr.value / 100))
                            .toDisplayableNumber()
                    InfoCard(
                        title = stringResource(R.string.change_last_24h),
                        formattedText = absoluteChangeFormatted.formatted,
                        icon = if (coinUi.changePercent24Hr.value > 0.0)
                            ImageVector.vectorResource(R.drawable.trending)
                        else
                            ImageVector.vectorResource(R.drawable.trending_down),
                    )
                }

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailsScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailsScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}