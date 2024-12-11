package com.ruslanlialko.currencyexchanger.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ruslanlialko.currencyexchanger.presentation.ui.home.HomeScreen
import com.ruslanlialko.currencyexchanger.presentation.ui.settings.SettingsScreen
import com.ruslanlialko.currencyexchanger.presentation.ui.transactions.TransactionsScreen

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided!") }

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    topLevelRoute.icon,
                                    contentDescription = stringResource(topLevelRoute.nameId)
                                )
                            },
                            label = { Text(text = stringResource(topLevelRoute.nameId)) },
                            selected = selectedIndex == index,
                            colors = NavigationBarItemDefaults.colors().copy(
                                selectedIndicatorColor = Color.Transparent,
                                selectedIconColor = MaterialTheme.colorScheme.secondary,
                                selectedTextColor = MaterialTheme.colorScheme.secondary,
                                unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                                unselectedTextColor = MaterialTheme.colorScheme.tertiary,
                            ),
                            onClick = {
                                selectedIndex = index
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }
                }
            }) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = Home) {
                    composable<Home> {
                        HomeScreen()
                    }
                    composable<Transactions> {
                        TransactionsScreen()
                    }
                    composable<Settings> {
                        SettingsScreen()
                    }
                }
            }
        }
    }
}