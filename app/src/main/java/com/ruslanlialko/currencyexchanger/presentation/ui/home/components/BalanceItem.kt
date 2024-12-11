package com.ruslanlialko.currencyexchanger.presentation.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.presentation.theme.CurrencyExchangerTheme
import com.ruslanlialko.currencyexchanger.presentation.utils.formatAmount
import com.ruslanlialko.currencyexchanger.presentation.utils.getFlagEmoji

@Composable
fun BalanceItem(balance: Balance) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .defaultMinSize(
                    minWidth = 92.dp,
                ),
        ) {
            Text(
                text = balance.currency.getFlagEmoji(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                ),
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(
                text = balance.value.formatAmount(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                ),
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = balance.currency,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                ),
                modifier = Modifier.padding(end = 8.dp),
            )

        }
    }
}

@Preview
@Composable
private fun Preview() {
    CurrencyExchangerTheme {
        BalanceItem(
            balance = Balance(
                currency = "USD",
                value = 100.0,
            )
        )
    }
}
