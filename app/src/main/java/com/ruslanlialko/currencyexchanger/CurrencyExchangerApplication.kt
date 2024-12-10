package com.ruslanlialko.currencyexchanger

import android.app.Application
import com.ruslanlialko.currencyexchanger.di.appModule
import com.ruslanlialko.currencyexchanger.di.dataModule
import com.ruslanlialko.currencyexchanger.di.networkModule
import com.ruslanlialko.currencyexchanger.di.repositoryModule
import com.ruslanlialko.currencyexchanger.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CurrencyExchangerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CurrencyExchangerApplication)
            modules(networkModule, dataModule, repositoryModule, useCaseModule, appModule)
        }
    }
}