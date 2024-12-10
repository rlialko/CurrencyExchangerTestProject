package com.ruslanlialko.currencyexchanger.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ruslanlialko.currencyexchanger.R


data class TopLevelRoute<T : Screen>(
    val route: T,
    val screenTitleId: Int,
    val nameId: Int,
    val icon: ImageVector
)

val topLevelRoutes = listOf(
    TopLevelRoute(Home, R.string.home_screen_title, R.string.home_tab, Icons.Filled.Home),
    TopLevelRoute(Transactions, R.string.transactions_screen_title, R.string.transactions_tab, Icons.Filled.DateRange
    ),
    TopLevelRoute(Settings, R.string.settings_screen_title, R.string.settings_tab, Icons.Filled.Settings)
)
