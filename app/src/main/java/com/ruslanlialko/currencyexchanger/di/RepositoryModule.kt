package com.ruslanlialko.currencyexchanger.di

import com.ruslanlialko.currencyexchanger.data.repository.BalanceRepositoryImpl
import com.ruslanlialko.currencyexchanger.data.repository.RatesRepositoryImpl
import com.ruslanlialko.currencyexchanger.data.repository.TransactionsRepositoryImpl
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import com.ruslanlialko.currencyexchanger.domain.repository.RatesRepository
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<RatesRepository> { RatesRepositoryImpl(get(), get()) }
    factory<BalanceRepository> { BalanceRepositoryImpl(get()) }
    factory<TransactionsRepository> { TransactionsRepositoryImpl(get()) }

}