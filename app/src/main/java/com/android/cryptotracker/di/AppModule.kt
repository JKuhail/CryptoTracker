package com.android.cryptotracker.di

import com.android.cryptotracker.core.data.networking.HttpClientFactory
import com.android.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.android.cryptotracker.crypto.domain.CoinDataSource
import com.android.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin is a dependency injection framework for Kotlin.
 * The 'AppModule' class works as a guidance for Koin to know which dependencies to inject.
 *
 * After we defined the 'HttpClientFactory' dependency, Koin will understand that it's the dependency
 * used for the httpClient inside 'RemoteCoinDataSource', and that's why there's no need to
 * hardcode it and we just used get().
 *
 * We also used the 'singleOf' function to bind the 'RemoteCoinDataSource' to the 'CoinDataSource'
 *
 * 'singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()'
 * equals to 'single { RemoteCoinDataSource(get()) }.bind<CoinDataSource>()'
 */
val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}