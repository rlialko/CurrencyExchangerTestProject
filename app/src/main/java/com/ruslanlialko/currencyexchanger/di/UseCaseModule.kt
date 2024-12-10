package com.ruslanlialko.currencyexchanger.di

import com.ruslanlialko.currencyexchanger.domain.use_case.CalculateReceiveAmountUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ExchangeUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.GetRatesUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ObserveBalancesUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ObserveTransactionsUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ResetInitialDataUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetRatesUseCase(get()) }
    factory { ObserveBalancesUseCase(get()) }
    factory { ResetInitialDataUseCase(get(), get()) }
    factory { CalculateReceiveAmountUseCase(get()) }
    factory { ExchangeUseCase(get(), get(), get()) }
    factory { ObserveTransactionsUseCase(get()) }
}