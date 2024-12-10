package com.ruslanlialko.currencyexchanger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ruslanlialko.currencyexchanger.presentation.navigation.AppNavigation
import com.ruslanlialko.currencyexchanger.presentation.theme.CurrencyExchangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangerTheme {
                AppNavigation()
            }
        }
    }
}