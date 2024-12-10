package com.ruslanlialko.currencyexchanger.di

import com.ruslanlialko.currencyexchanger.presentation.ui.home.HomeViewModel
import com.ruslanlialko.currencyexchanger.presentation.ui.settings.SettingsViewModel
import com.ruslanlialko.currencyexchanger.presentation.ui.transactions.TransactionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { TransactionsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}