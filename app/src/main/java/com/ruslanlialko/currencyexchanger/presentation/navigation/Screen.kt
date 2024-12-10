package com.ruslanlialko.currencyexchanger.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen

@Serializable
data object Home : Screen()

@Serializable
data object Transactions : Screen()

@Serializable
data object Settings : Screen()
