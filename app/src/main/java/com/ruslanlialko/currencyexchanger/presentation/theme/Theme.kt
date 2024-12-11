package com.ruslanlialko.currencyexchanger.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BradBlack,
    secondary = BradGold,
    tertiary = BrandSilver,

    surfaceContainer = BradBlack, // BottomAppBar background
    background = BradBlack,
    surface = Color.White,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = DarkGrey,
    onSurfaceVariant = LightGrey
)

@Composable
fun CurrencyExchangerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}