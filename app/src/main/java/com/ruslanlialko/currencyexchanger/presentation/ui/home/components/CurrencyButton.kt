package com.ruslanlialko.currencyexchanger.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruslanlialko.currencyexchanger.presentation.utils.getFlagEmoji

@Composable
fun CurrencyButton(currency: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = currency.getFlagEmoji(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 16.sp,
                lineHeight = 20.sp,
            ),
            modifier = Modifier.padding(end = 16.dp),
        )

        Text(
            text = currency,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                lineHeight = 20.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)

        )
    }
}