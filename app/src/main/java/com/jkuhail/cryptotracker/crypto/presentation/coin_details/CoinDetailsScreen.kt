@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.jkuhail.cryptotracker.crypto.presentation.coin_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    AnimatedVisibility(
                        visible = coinUi.coinPriceHistory.isNotEmpty()
                    ) {
                        var selectedDataPoint by remember {
                            mutableStateOf<DataPoint?>(null)
                        }
                        var labelWidth by remember {
                            mutableFloatStateOf(0f)
                        }
                        var totalChartWidth by remember {
                            mutableFloatStateOf(0f)
                        }
                        val amountOfVisibleDataPoints = if(labelWidth > 0) {
                            ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                        } else {
                            0
                        }
                        val startIndex = (coinUi.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                            .coerceAtLeast(0)
                        LineChart(
                            dataPoints = coinUi.coinPriceHistory,
                            style = ChartStyle(
                                chartLineColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.3f
                                ),
                                selectedColor = MaterialTheme.colorScheme.primary,
                                helperLinesThicknessPx = 5f,
                                axisLinesThicknessPx = 5f,
                                labelFontSize = 14.sp,
                                minYLabelSpacing = 25.dp,
                                verticalPadding = 8.dp,
                                horizontalPadding = 8.dp,
                                xAxisLabelSpacing = 8.dp
                            ),
                            visibleDataPointsIndices = startIndex..coinUi.coinPriceHistory.lastIndex,
                            unit = "$",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .onSizeChanged { totalChartWidth = it.width.toFloat() },
                            selectedDataPoint = selectedDataPoint,
                            onSelectedDataPoint = {
                                selectedDataPoint = it
                            },
                            onXLabelWidthChange = { labelWidth = it }
                        )
                    }
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